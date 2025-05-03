package com.xelari.presencebot.telegram.operation.scenario.team.join;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class JoinTeamCallbackHandler implements CallbackHandler {
    
    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {
        SendMessage message = new SendMessage(
                update.getCallbackQuery().getMessage().getChatId().toString(),
                Constants.JOIN_TEAM_MESSAGE
        );
        message.enableHtml(true);
        return message;
    }
}
