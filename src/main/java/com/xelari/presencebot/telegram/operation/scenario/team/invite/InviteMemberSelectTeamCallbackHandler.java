package com.xelari.presencebot.telegram.operation.scenario.team.invite;

import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.usecase.team.FindManagingTeamsUseCase;
import com.xelari.presencebot.domain.entity.team.Team;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.JsonHandler;
import com.xelari.presencebot.telegram.operation.callback.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@TelegramCallback(CallbackType.INVITE_MEMBER)
public class InviteMemberSelectTeamCallbackHandler implements CallbackHandler {

    private final FindManagingTeamsUseCase findManagingTeamsUseCase;
    private final CallbackDataCache callbackDataCache;

    @Override
    public SendMessage apply(Callback callback, Update update) {

        var userId = getUserId(update);
        var chatId = getChatId(update);

        var message = new SendMessage();
        message.setChatId(chatId);

        try {
            var managingTeams = findManagingTeamsUseCase.execute(userId);

            if (managingTeams.isEmpty()) {
                message.setText(Constants.USER_HAVE_NOT_MANAGING_TEAMS_MESSAGE);
            } else {
                message.setText(Constants.SELECT_TEAM_MESSAGE);
                addKeyboard(message, managingTeams);
            }

        } catch (UserNotFoundException e) {
            message.setText(Constants.USER_NOT_FOUND_MESSAGE);
        }

        return message;
    }

    private void addKeyboard(SendMessage sendMessage, List<Team> managingTeams) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (int i = 0; i < managingTeams.size(); i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(
                    (i + 1) + ". " + managingTeams.get(i).getName()
                            + " [ " + managingTeams.get(i).getMembers().size() + " members ]"
            );
            try {
                inlineKeyboardButton.setCallbackData(
                        JsonHandler.toJson(
                                new Callback(
                                        CallbackType.INVITE_MEMBER_SELECT_ROLE,
                                        callbackDataCache.putData(managingTeams.get(i).getId())
                                )
                        )
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            rowList.add(List.of(inlineKeyboardButton));
        }

        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }

}

