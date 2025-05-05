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
public class CreateMeetingEnterRepeatCountCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final DialogDispatcher dialogDispatcher;
    private final DialogDataCache dialogDataCache;

    private final CreateMeetingFinalDialogHandler createMeetingFinalDialogHandler;

    @Override
    public SendMessage apply(Callback callback, Update update) {

        var chatId = update.getCallbackQuery().getMessage().getChatId();
        var request = callbackDataCache.getData(callback, CreateMeetingRequest.class);

        dialogDataCache.putData(chatId, request);
        dialogDispatcher.putHandler(chatId, createMeetingFinalDialogHandler);

        return new SendMessage(
                String.valueOf(chatId),
                Constants.ENTER_MEETING_REPEAT_COUNT_MESSAGE
        );
    }
}
