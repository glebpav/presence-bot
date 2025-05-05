package com.xelari.presencebot.telegram.operation.scenario.team.join;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class JoinTeamCallbackHandler implements CallbackHandler {

    private final DialogDispatcher dialogDispatcher;
    private final JoinTeamDialogHandler joinTeamDialogHandler;
    
    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var chatId = update.getCallbackQuery().getMessage().getChatId();

        var message = new SendMessage(
                update.getCallbackQuery().getMessage().getChatId().toString(),
                Constants.JOIN_TEAM_MESSAGE
        );

        dialogDispatcher.putHandler(chatId, joinTeamDialogHandler);

        return message;
    }
}
