package com.xelari.presencebot.telegram.operation.scenario.start;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class HealthCommand implements CommandHandler {

    @Override
    public SendMessage apply(Update update) {

        var chatId = getChatId(update);

        return new SendMessage(
                String.valueOf(chatId),
                Constants.HEALTH_MESSAGE
        );

    }

}
