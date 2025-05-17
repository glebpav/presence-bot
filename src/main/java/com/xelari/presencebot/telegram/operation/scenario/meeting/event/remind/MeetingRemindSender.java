package com.xelari.presencebot.telegram.operation.scenario.meeting.event.remind;

import com.xelari.presencebot.application.adapter.dto.meeting.MeetingResponse;
import com.xelari.presencebot.application.usecase.meeting.event.remind.RemindMeetingUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.TelegramBot;
import com.xelari.presencebot.telegram.operation.callback.CallbackType;
import com.xelari.presencebot.telegram.ui.ButtonBuilder;
import com.xelari.presencebot.telegram.ui.ButtonDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


@Component
public class MeetingRemindSender {

    private TelegramBot telegramBot;
    private final ButtonBuilder buttonBuilder;

    @Autowired
    MeetingRemindSender(
            RemindMeetingUseCase remindMeetingUseCase,
            TelegramBot telegramBot,
            ButtonBuilder buttonBuilder
    ) {

        this.telegramBot = telegramBot;
        this.buttonBuilder = buttonBuilder;
        remindMeetingUseCase.setOnRemindMeetingListener(onRemindMeeting);

    }

    RemindMeetingUseCase.OnRemindMeetingListener onRemindMeeting = (backConnection, meetingResponse) -> {

        var message = new SendMessage(
                String.valueOf(backConnection),
                Constants.MEETING_ALERT_MESSAGE(
                        meetingResponse
                )
        );

        addKeyboard(message, meetingResponse);

        try {
            telegramBot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    };

    private void addKeyboard(SendMessage message, MeetingResponse meetingResponse) {
        buttonBuilder.addHorizontalKeyboard(
                message,
                List.of(
                        new ButtonDescription(
                                "I will",
                                CallbackType.WILL_PRESENT,
                                meetingResponse.id()
                        ),
                        new ButtonDescription(
                                "I will be absent",
                                CallbackType.WILL_NOT_PRESENT,
                                meetingResponse.id()
                        )
                )
                );
    }
}
