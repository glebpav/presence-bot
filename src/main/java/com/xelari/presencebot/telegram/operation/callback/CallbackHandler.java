package com.xelari.presencebot.telegram.operation.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.UuidHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

public interface CallbackHandler {
    SendMessage apply(Callback callback, Update update) throws JsonProcessingException;

    default UUID getUserId(Update update) {
        return UuidHandler.longToUUID(
                update.getCallbackQuery().getFrom().getId()
        );
    }

    default Long getChatId(Update update) {
        return update.getCallbackQuery().getFrom().getId();
    }
}
