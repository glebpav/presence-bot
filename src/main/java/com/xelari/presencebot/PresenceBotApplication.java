package com.xelari.presencebot;

import com.xelari.presencebot.application.usecase.meeting.event.remind.MeetingReminderConfig;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(MeetingReminderConfig.class)
public class PresenceBotApplication {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().directory("env/.env").load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(PresenceBotApplication.class, args);

    }
}
