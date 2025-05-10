package com.xelari.presencebot.telegram.operation.scenario.team.invite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.dto.team.CreateTeamTokenRequest;
import com.xelari.presencebot.application.usecase.team.CreateInviteTokenUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@TelegramCallback(CallbackType.INVITE_MEMBER_GENERATE_TOKEN)
public class InviteMemberGenerateTokenCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final CreateInviteTokenUseCase createInviteTokenUseCase;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var chatId = getChatId(update);
        var createTeamTokenRequest = callbackDataCache.getData(
                callback, CreateTeamTokenRequest.class
        );

        var token = createInviteTokenUseCase.execute(createTeamTokenRequest);

        SendMessage sendMessage = new SendMessage(
                chatId.toString(),
                Constants.SUCCESSFUL_CREATE_INITIATION_TOKEN_MESSAGE(token)
        );

        sendMessage.enableHtml(true);

        return sendMessage;
    }
}
