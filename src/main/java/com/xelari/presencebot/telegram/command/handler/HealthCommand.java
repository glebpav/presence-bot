package com.xelari.presencebot.telegram.command.handler;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.command.CommandHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public class HealthCommand implements CommandHandler {
    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(Constants.HEALTH_MESSAGE);
        return sendMessage;
    }
}
