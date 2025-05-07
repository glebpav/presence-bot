package com.xelari.presencebot.telegram.operation.scenario.meeting.edit.schedule;

import com.xelari.presencebot.application.dto.meeting.ChangeMeetingTimeRequest;
import com.xelari.presencebot.application.exception.meeting.MeetingsNotFoundException;
import com.xelari.presencebot.application.usecase.meeting.ChangeMeetingTimeUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EditMeetingChangeTimeDialogHandler implements DialogHandler {

    private final DialogDataCache dialogDataCache;
    private final ChangeMeetingTimeUseCase changeMeetingTimeUseCase;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

    @Override
    public SendMessage apply(Update update, long chatId) {

        var meetingId = dialogDataCache.getData(chatId, UUID.class);
        var newDataString = update.getMessage().getText();

        var message = new SendMessage();
        message.setChatId(chatId);

        if (newDataString == null || newDataString.isBlank()) {
            message.setText(Constants.INPUT_CANT_BE_BLANK_MESSAGE);
            return message;
        }

        LocalDateTime newDate;
        try {
            newDate = LocalDateTime.parse(
                    newDataString.trim(),
                    dateTimeFormatter
            );
        } catch (DateTimeParseException e) {
            message.setText(Constants.CANT_UNDERSTAND);
            return message;
        }

        var request = new ChangeMeetingTimeRequest(meetingId, newDate);

        try {
            changeMeetingTimeUseCase.execute(request);
        } catch (MeetingsNotFoundException e) {
            message.setText(Constants.NO_MATCHING_MEETING_IN_LIST_MESSAGE);
            return message;
        }

        message.setText(Constants.MEETING_TIME_WAS_CHANGED_SUCCESSFULLY_MESSAGE);
        return message;
    }
}
