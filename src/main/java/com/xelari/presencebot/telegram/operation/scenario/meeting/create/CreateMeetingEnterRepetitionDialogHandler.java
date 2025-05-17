package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.xelari.presencebot.application.adapter.dto.meeting.CreateMeetingRequest;
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

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateMeetingEnterRepetitionDialogHandler implements DialogHandler {

    private final ButtonBuilder buttonBuilder;
    private final DialogDataCache dialogDataCache;

    @Override
    public SendMessage apply(Update update, long chatId) {

        var request = dialogDataCache.getData(chatId, CreateMeetingRequest.class);

        if (request == null || request.teamId() == null) {
            throw new RuntimeException("Data should be set");
        }

        var message = new SendMessage();
        message.setChatId(chatId);

        var durationMinutesString = update.getMessage().getText();

        if (durationMinutesString == null || durationMinutesString.isBlank()) {
            message.setText(Constants.CANT_UNDERSTAND);
            return message;
        }

        try {
            var durationMinutes = Integer.parseInt(durationMinutesString);
            request = request.withDurationMinutes(durationMinutes);
            message.setText(Constants.SELECT_MEETING_REPETITIONS_MESSAGE);
            addKeyboard(message, request);
        } catch (NumberFormatException e) {
            message.setText(Constants.CANT_UNDERSTAND);
            return message;
        }

        return message;
    }

    private void addKeyboard(SendMessage message, CreateMeetingRequest data) {

        List<ButtonDescription> buttonsDescription = List.of(
                new ButtonDescription(MeetingRepeat.NONE.getDescription(),
                        CallbackType.CREATE_MEETING_REPEAT_SELECTED,
                        data.withMeetingRepeat(MeetingRepeat.NONE)
                ),
                new ButtonDescription(MeetingRepeat.EVERY_DAY.getDescription(),
                        CallbackType.CREATE_MEETING_REPEAT_SELECTED,
                        data.withMeetingRepeat(MeetingRepeat.EVERY_DAY)
                ),
                new ButtonDescription(MeetingRepeat.EVERY_MONTH.getDescription(),
                        CallbackType.CREATE_MEETING_REPEAT_SELECTED,
                        data.withMeetingRepeat(MeetingRepeat.EVERY_MONTH)
                ),
                new ButtonDescription(MeetingRepeat.EVERY_WEEK.getDescription(),
                        CallbackType.CREATE_MEETING_REPEAT_SELECTED,
                        data.withMeetingRepeat(MeetingRepeat.EVERY_WEEK)
                )
        );

        buttonBuilder.addVerticalKeyboard(message, buttonsDescription);
    }

}
