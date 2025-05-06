package com.xelari.presencebot.telegram.operation.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    SendMessage apply(Update update);

    default long getChatId(Update update) {
        return update.getMessage().getChatId();
    }
}
