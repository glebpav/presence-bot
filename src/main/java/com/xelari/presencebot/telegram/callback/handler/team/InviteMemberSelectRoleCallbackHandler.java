package com.xelari.presencebot.telegram.callback.handler.team;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.dto.team.CreateTeamTokenRequest;
import com.xelari.presencebot.domain.entity.TeamMember;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.JsonHandler;
import com.xelari.presencebot.telegram.callback.Callback;
import com.xelari.presencebot.telegram.callback.CallbackDataCache;
import com.xelari.presencebot.telegram.callback.CallbackHandler;
import com.xelari.presencebot.telegram.callback.CallbackType;
import com.xelari.presencebot.telegram.config.InvitationTokenConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InviteMemberSelectRoleCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final InvitationTokenConfig invitationTokenConfig;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        System.out.println(callback.getDataKey());

        var teamId = callbackDataCache.getData(callback, UUID.class);

        SendMessage message = new SendMessage();
        message.setChatId(update.getCallbackQuery().getMessage().getChatId());
        message.setText(Constants.SELECT_ROLE_MESSAGE);

        addKeyboard(message, List.of(TeamMember.Role.values()), teamId);

        return message;
    }

    private void addKeyboard(SendMessage sendMessage, List<TeamMember.Role> availableRole, UUID teamId) throws JsonProcessingException {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (int i = 0; i < availableRole.size(); i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText((i + 1) + ". " + availableRole.get(i));
            inlineKeyboardButton.setCallbackData(
                    JsonHandler.toJson(
                            new Callback(
                                    CallbackType.GENERATE_TOKEN_FOR_INVITATION,
                                    callbackDataCache.putData(
                                            new CreateTeamTokenRequest(
                                                    teamId,
                                                    availableRole.get(i),
                                                    LocalDateTime.now().plusDays(
                                                            invitationTokenConfig.getExpirationDays()
                                                    )
                                            )
                                    )
                            )
                    )
            );
            rowList.add(List.of(inlineKeyboardButton));
        }

        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }

}
