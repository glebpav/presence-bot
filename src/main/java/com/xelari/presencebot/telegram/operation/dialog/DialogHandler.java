package com.xelari.presencebot.telegram.operation.dialog;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface DialogHandler {
    SendMessage apply(Update update, long chatId);
}
