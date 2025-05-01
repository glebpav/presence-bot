package com.xelari.presencebot.telegram.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackHandler {
    SendMessage apply(Callback callback, Update update) throws JsonProcessingException;
}
