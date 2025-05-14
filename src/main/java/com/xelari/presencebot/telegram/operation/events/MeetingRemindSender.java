package com.xelari.presencebot.telegram.operation.events;

import com.xelari.presencebot.application.usecase.meeting.event.remind.RemindMeetingUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class MeetingRemindSender {

    private TelegramBot telegramBot;

    MeetingRemindSender(
            @Autowired RemindMeetingUseCase remindMeetingUseCase,
            @Autowired TelegramBot telegramBot
    ) {

        this.telegramBot = telegramBot;
        remindMeetingUseCase.setOnRemindMeetingListener(onRemindMeeting);

    }

    RemindMeetingUseCase.OnRemindMeetingListener onRemindMeeting = (backConnection, meetingResponse) -> {

        var message = new SendMessage(
                String.valueOf(backConnection),
                Constants.MEETING_ALERT_MESSAGE(
                        meetingResponse
                )
        );

        try {
            telegramBot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    };
}
