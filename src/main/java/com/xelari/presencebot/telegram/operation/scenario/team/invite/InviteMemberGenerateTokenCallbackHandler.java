package com.xelari.presencebot.telegram.operation.scenario.team.invite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.dto.team.CreateTeamTokenRequest;
import com.xelari.presencebot.application.usecase.team.GenerateInviteTokenUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackDataCache;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class InviteMemberGenerateTokenCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final GenerateInviteTokenUseCase generateInviteTokenUseCase;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        var createTeamTokenRequest = callbackDataCache.getData(callback, CreateTeamTokenRequest.class);

        var token = generateInviteTokenUseCase.execute(createTeamTokenRequest);

        SendMessage sendMessage = new SendMessage(
                chatId,
                Constants.SUCCESSFUL_CREATE_INITIATION_TOKEN_MESSAGE(token)
        );

        sendMessage.enableHtml(true);

        return sendMessage;
    }
}
