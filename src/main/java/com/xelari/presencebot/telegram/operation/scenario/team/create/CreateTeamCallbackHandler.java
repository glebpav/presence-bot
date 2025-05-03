package com.xelari.presencebot.telegram.operation.scenario.team.create;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CreateTeamCallbackHandler implements CallbackHandler {

    @Override
    public SendMessage apply(Callback callback, Update update) {
        var message = new SendMessage(
                update.getCallbackQuery().getMessage().getChatId().toString(),
                Constants.ENTER_TEAM_NAME_MESSAGE
        );
        message.enableHtml(true);
        return message;
    }

}
