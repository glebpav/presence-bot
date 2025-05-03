package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.xelari.presencebot.application.dto.meeting.CreateMeetingRequest;
import com.xelari.presencebot.domain.valueobject.meeting.MeetingRepeat;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.CallbackType;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import com.xelari.presencebot.telegram.ui.ButtonBuilder;
import com.xelari.presencebot.telegram.ui.ButtonDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateMeetingSetRepetitionDialogHandler implements DialogHandler {

    private final ButtonBuilder buttonBuilder;
    private final DialogDataCache dialogDataCache;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

    @Override
    public SendMessage apply(Update update, long chatId) {

        System.out.println("in repetition dialog");

        var data = dialogDataCache.getData(chatId, CreateMeetingRequest.class);

        if (data == null || data.teamId() == null) {
            throw new RuntimeException("Data should be set");
        }

        var message = new SendMessage();
        message.setChatId(chatId);

        var text = update.getMessage().getText();

        if (text == null || text.trim().isEmpty()) {
            message.setText(Constants.INCORRECT_TIME_FORMAT_MESSAGE);
        } else {
            try {
                LocalDateTime time = LocalDateTime.parse(
                        text.trim(),
                        dateTimeFormatter
                );
                data = data.withTime(time);
                message.setText(Constants.SELECT_MEETING_REPETITIONS_MESSAGE);
                addKeyboard(message, data);
            } catch (DateTimeParseException e) {
                message.setText(Constants.INCORRECT_TIME_FORMAT_MESSAGE);
            }
        }

        return message;
    }

    private void addKeyboard(SendMessage message, CreateMeetingRequest data) {

        List<ButtonDescription> buttonsDescription = List.of(
                new ButtonDescription(MeetingRepeat.NONE.getDescription(),
                        CallbackType.CREATE_MEETING_REPEAT_NONE,
                        data
                ),
                new ButtonDescription(MeetingRepeat.EVERY_DAY.getDescription(),
                        CallbackType.CREATE_MEETING_REPEAT_EVERY_DAY,
                        data
                ),
                new ButtonDescription(MeetingRepeat.EVERY_MONTH.getDescription(),
                        CallbackType.CREATE_MEETING_REPEAT_EVERY_MONTH,
                        data
                ),
                new ButtonDescription(MeetingRepeat.EVERY_WEEK.getDescription(),
                        CallbackType.CREATE_MEETING_REPEAT_EVERY_WEEK,
                        data
                )
        );

        buttonBuilder.addVerticalKeyboard(message, buttonsDescription);
    }

}
