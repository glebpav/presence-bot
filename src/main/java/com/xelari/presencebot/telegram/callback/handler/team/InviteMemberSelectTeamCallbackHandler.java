package com.xelari.presencebot.telegram.callback.handler.team;

import com.xelari.presencebot.application.exception.UserNotFoundException;
import com.xelari.presencebot.application.usecase.team.FindManagingTeamsUseCase;
import com.xelari.presencebot.domain.entity.Team;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.JsonHandler;
import com.xelari.presencebot.telegram.UuidHandler;
import com.xelari.presencebot.telegram.callback.Callback;
import com.xelari.presencebot.telegram.callback.CallbackDataCache;
import com.xelari.presencebot.telegram.callback.CallbackHandler;
import com.xelari.presencebot.telegram.callback.CallbackType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InviteMemberSelectTeamCallbackHandler implements CallbackHandler {

    private final FindManagingTeamsUseCase findManagingTeamsUseCase;
    private final CallbackDataCache callbackDataCache;

    @Override
    public SendMessage apply(Callback callback, Update update) {

        System.out.println("Is inside");

        var userId = UuidHandler.longToUUID(update.getCallbackQuery().getFrom().getId());

        SendMessage message = new SendMessage();
        message.setChatId(update.getCallbackQuery().getMessage().getChatId());

        try {
            List<Team> managingTeams = findManagingTeamsUseCase.execute(userId);

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
                                        CallbackType.SELECT_ROLE_FOR_INVITATION,
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
