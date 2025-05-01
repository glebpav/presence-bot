package com.xelari.presencebot.telegram.callback;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.JsonHandler;
import com.xelari.presencebot.telegram.callback.handler.team.CreateTeamCallbackHandler;
import com.xelari.presencebot.telegram.command.handler.team.CreateTeamCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CallbackDispatcher {

    private final Map<CallbackType, CallbackHandler> callbacks;

    public CallbackDispatcher(
            @Autowired CreateTeamCallbackHandler createTeamCallbackHandler
    ) {
        // TODO: fill callback map
        this.callbacks = Map.of(
                CallbackType.CREATE_TEAM, createTeamCallbackHandler
        );

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
