package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.xelari.presencebot.application.dto.meeting.CreateMeetingRequest;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CreateMeetingEnterDescriptionDialogHandler implements DialogHandler {

    private final DialogDataCache dialogDataCache;
    private final DialogDispatcher dialogDispatcher;

    private final CreateMeetingEnterTimeDialogHandler createMeetingEnterTimeDialogHandler;

    @Override
    public SendMessage apply(Update update, long chatId) {

        var request = dialogDataCache.getData(
                chatId,
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
