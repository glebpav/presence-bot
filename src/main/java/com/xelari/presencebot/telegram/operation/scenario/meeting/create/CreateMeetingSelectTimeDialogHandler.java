package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.xelari.presencebot.domain.valueobject.meeting.MeetingRepeat;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.CallbackType;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import com.xelari.presencebot.telegram.ui.ButtonBuilder;
import com.xelari.presencebot.telegram.ui.ButtonDescription;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateMeetingSelectTimeDialogHandler implements DialogHandler {

    @Setter
    @Getter
    private class UserData {
        UUID teamId;
        LocalDateTime time;
        int durationMinutes;
    }

    private final ButtonBuilder buttonBuilder;

    private final Map<Long, UserData> cashedData = new HashMap<>();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public SendMessage apply(Update update, long chatId) {

        var data = cashedData.get(chatId);

        if (data == null || data.teamId == null) {
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
                data.setTime(time);
                message.setText(Constants.SELECT_MEETING_REPETITIONS_MESSAGE);
                addKeyboard(message, data);
            } catch (DateTimeParseException e) {
                message.setText(Constants.INCORRECT_TIME_FORMAT_MESSAGE);
            }
        }

        return message;
    }

    private void addKeyboard(SendMessage message, UserData data) {

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

    public void prepareTeam(long chatId, UUID teamId) {
        var userData = new UserData();
        userData.setTeamId(teamId);
        cashedData.put(chatId, userData);
    }

}
