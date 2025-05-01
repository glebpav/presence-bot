package com.xelari.presencebot.telegram.callback.handler.team;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.callback.Callback;
import com.xelari.presencebot.telegram.callback.CallbackHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CreateTeamCallbackHandler implements CallbackHandler {

    @Override
    public SendMessage apply(Callback callback, Update update) {
        // System.out.println("Inside CreateTeamCallbackHandler");
        return new SendMessage(
                update.getCallbackQuery().getMessage().getChatId().toString(),
                Constants.ENTER_TEAM_NAME_MESSAGE
        );
    }

}
