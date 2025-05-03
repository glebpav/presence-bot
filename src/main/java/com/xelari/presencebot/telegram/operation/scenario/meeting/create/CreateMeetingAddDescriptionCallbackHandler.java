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

@Component
@RequiredArgsConstructor
public class CreateMeetingAddDescriptionCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final DialogDataCache dialogDataCache;
    private final DialogDispatcher dialogDispatcher;
    private final CreateMeetingEnterTimeDialogHandler createMeetingEnterTimeDialogHandler;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var chatId = update.getCallbackQuery().getMessage().getChatId();

        var request = callbackDataCache.getData(
                callback,
                CreateMeetingRequest.class
        );

        var message = new SendMessage(
                String.valueOf(chatId),
                Constants.ENTER_MEETING_DESCRIPTION_MESSAGE
        );

        dialogDataCache.put(
                chatId,
                request
        );

        dialogDispatcher.putHandler(
                chatId,
                createMeetingEnterTimeDialogHandler
        );

        return message;
    }
}
