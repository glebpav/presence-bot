package com.xelari.presencebot.telegram.operation.dialog;

import com.xelari.presencebot.telegram.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DialogDispatcher {

    private final Map<Long, DialogHandler> handlers = new HashMap<>();

    public SendMessage handleDialogs(Update update) {

        long chatId = update.getMessage().getChatId();

        SendMessage answer;
        var callbackBiFunction = handlers.get(chatId);

        if (callbackBiFunction != null) {
            answer = callbackBiFunction.apply(update, chatId);
        } else {
            return new SendMessage(String.valueOf(chatId), Constants.UNKNOWN_COMMAND);
        }

        return answer;
    }

    public void putHandler(long chatId, DialogHandler handler) {
        handlers.put(chatId, handler);
    }

    public void removeHandler(long chatId) {
        handlers.remove(chatId);
    }
}
