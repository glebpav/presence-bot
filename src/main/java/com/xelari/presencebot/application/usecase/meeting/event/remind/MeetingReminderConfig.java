package com.xelari.presencebot.application.usecase.meeting.event.remind;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "meeting.reminder")
public class MeetingReminderConfig {

    @Value("${notification-interval-minutes}")
    private long notificationIntervalMinutes = 30;

}
