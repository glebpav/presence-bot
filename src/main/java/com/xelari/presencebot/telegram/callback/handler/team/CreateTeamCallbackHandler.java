package com.xelari.presencebot.telegram.callback.handler.team;

import com.xelari.presencebot.telegram.callback.Callback;
import com.xelari.presencebot.telegram.callback.CallbackHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CreateTeamCallbackHandler implements CallbackHandler {



    @Override
    public SendMessage apply(Callback callback, Update update) {
        return null;
    }

}
