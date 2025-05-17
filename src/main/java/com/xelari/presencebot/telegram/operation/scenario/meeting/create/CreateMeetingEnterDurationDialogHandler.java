package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.xelari.presencebot.application.adapter.dto.meeting.CreateMeetingRequest;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@RequiredArgsConstructor
public class CreateMeetingEnterDurationDialogHandler implements DialogHandler {

    private final DialogDataCache dialogDataCache;
    private final DialogDispatcher dialogDispatcher;
    private final CreateMeetingEnterRepetitionDialogHandler createMeetingEnterRepetitionDialogHandler;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

    @Override
    public SendMessage apply(Update update, long chatId) {

        var message = new SendMessage();
        message.setChatId(chatId);

        var request = dialogDataCache.getData(chatId, CreateMeetingRequest.class);

        var text = update.getMessage().getText();

        if (text == null || text.trim().isEmpty()) {
            message.setText(Constants.INCORRECT_TIME_FORMAT_MESSAGE);
            return message;
        }

        try {
            LocalDateTime time = LocalDateTime.parse(
                    text.trim(),
                    dateTimeFormatter
            );
            request = request.withTime(time);
            message.setText(Constants.ENTER_MEETING_DURATION_MESSAGE);
        } catch (DateTimeParseException e) {
            message.setText(Constants.INCORRECT_TIME_FORMAT_MESSAGE);
            return message;
        }

        dialogDataCache.putData(chatId, request);
        dialogDispatcher.putHandler(chatId, createMeetingEnterRepetitionDialogHandler);

        return message;
    }
}
