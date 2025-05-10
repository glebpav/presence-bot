package com.xelari.presencebot.telegram.operation.scenario.common;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.command.CommandHandler;
import com.xelari.presencebot.telegram.operation.command.TelegramCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@TelegramCommand("/health")
public class HealthCommandHandler implements CommandHandler {

    @Override
    public SendMessage apply(Update update) {

        var chatId = getChatId(update);

        return new SendMessage(
                String.valueOf(chatId),
                Constants.HEALTH_MESSAGE
        );

    }

}
