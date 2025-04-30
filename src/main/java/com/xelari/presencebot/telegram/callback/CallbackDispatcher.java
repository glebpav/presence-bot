package com.xelari.presencebot.telegram.callback;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.JsonHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CallbackDispatcher {

    private final Map<CallbackType, CallbackHandler> callbacks;

    public CallbackDispatcher() {
        // TODO: fill callback map
        callbacks = new HashMap<>();
        /*this.callbacks = Map.of(CallbackType.TYPE_CHOOSE, typeChooseCallback,
                CallbackType.CITY_CHOOSE, cityChooseCallback,
                CallbackType.ADDRESS_CHOOSE, addressChooseCallback,
                CallbackType.PIN_OK, pinReviewCallback,
                CallbackType.PIN_WRONG, pinReviewCallback,
                CallbackType.PIN_ADD, pinActionCallback,
                CallbackType.PIN_DONT_ADD, pinActionCallback
        );*/
    }

    public SendMessage handleCallbacks(Update update) {
        List<String> list = JsonHandler.toList(update.getCallbackQuery().getData());
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        SendMessage answer;
        if (list.isEmpty()) {
            answer = new SendMessage(String.valueOf(chatId), Constants.ERROR);
        } else {

            Callback callback = Callback.builder()
                    .callbackType(CallbackType.valueOf(list.get(0)))
                    .data(list.get(1))
                    .build();

            CallbackHandler callbackBiFunction = callbacks.get(callback.getCallbackType());
            answer = callbackBiFunction.apply(callback, update);
        }

        return answer;
    }

}
