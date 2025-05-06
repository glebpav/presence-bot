package com.xelari.presencebot.telegram.operation.dialog;

import com.xelari.presencebot.telegram.UuidHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

public interface DialogHandler {

    SendMessage apply(Update update, long chatId);

    default UUID getUserId(Update update) {
        return UuidHandler.longToUUID(
                update.getMessage().getFrom().getId()
        );
    }

}
