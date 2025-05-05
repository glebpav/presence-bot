package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.xelari.presencebot.application.dto.meeting.CreateMeetingRequest;
import com.xelari.presencebot.application.exception.InputCantBeEmpty;
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
public class CreateMeetingEnterTimeDialogHandler implements DialogHandler {

    private final DialogDataCache dialogDataCache;
    private final DialogDispatcher dialogDispatcher;

    private final CreateMeetingEnterDurationDialogHandler createMeetingEnterDurationDialogHandler;

    @Override
    public SendMessage apply(Update update, long chatId) {

        var data = dialogDataCache.getData(chatId, CreateMeetingRequest.class);
        var meetingDescription = update.getMessage().getText();

        if (meetingDescription == null || meetingDescription.isBlank()) {
            throw new InputCantBeEmpty();
        }

        data = data.withDescription(meetingDescription);

        dialogDataCache.putData(chatId, data);
        dialogDispatcher.putHandler(chatId, createMeetingEnterDurationDialogHandler);

        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(Constants.INPUT_MEETING_TIME_MESSAGE);
        message.enableHtml(true);

        return message;
    }

}
