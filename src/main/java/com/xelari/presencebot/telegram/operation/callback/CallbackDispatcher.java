package com.xelari.presencebot.telegram.operation.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.JsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CallbackDispatcher {

    private final Map<CallbackType, CallbackHandler> callbacks;

    @Autowired
    public CallbackDispatcher(List<CallbackHandler> allHandlers) {
        callbacks = allHandlers.stream()
                .filter(handler -> handler.getClass().isAnnotationPresent(TelegramCallback.class))
                .collect(Collectors.toMap(
                        handler -> handler.getClass().getAnnotation(TelegramCallback.class).value(),
                        handler -> handler
                ));
    }

    public SendMessage handleCallbacks(Update update) throws JsonProcessingException {

        String stringData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        SendMessage answer;
        if (stringData.isEmpty()) {
            answer = new SendMessage(String.valueOf(chatId), Constants.ERROR);
        } else {

            Callback callback;
            try {
                callback = JsonHandler.fromJson(stringData);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            CallbackHandler callbackBiFunction = callbacks.get(callback.getCallbackType());
            answer = callbackBiFunction.apply(callback, update);
        }

        return answer;
    }

}
