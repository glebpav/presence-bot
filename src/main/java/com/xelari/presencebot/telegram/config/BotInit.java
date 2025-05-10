package com.xelari.presencebot.telegram.config;

import com.xelari.presencebot.telegram.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Component
@Slf4j
public class BotInit {

    private final TelegramBot telegramBot;

    @Autowired
    public BotInit(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }

        SetMyCommands setMyCommands = new SetMyCommands();

        List<BotCommand> commands = List.of(
                new BotCommand("/start", "Register and start bot"),
                new BotCommand("/help", "Show help"),
                new BotCommand("/meeting", "Meetings options"),
                new BotCommand("/team", "Teams options")
        );

        setMyCommands.setCommands(commands);

        try {
            telegramBot.execute(setMyCommands);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }

    }


}
