package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.dto.meeting.CreateMeetingRequest;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackDataCache;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateMeetingEnterNameCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final DialogDataCache dialogDataCache;
    private final DialogDispatcher dialogDispatcher;

    private final CreateMeetingEnterDescriptionDialogHandler enterDescriptionCallbackHandler;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var chatId = update.getCallbackQuery().getMessage().getChatId();
        var teamId = callbackDataCache.getData(callback, UUID.class);

        SendMessage message = new SendMessage(
                chatId.toString(), Constants.ENTER_MEETING_NAME_MESSAGE
        );

        var createMeetingRequest = new CreateMeetingRequest(
                teamId, null, 0, null, null
        );

        dialogDataCache.put(
                chatId,
                createMeetingRequest
        );

        dialogDispatcher.putHandler(
                chatId,
                enterDescriptionCallbackHandler
        );

        return message;
    }
}
