package com.xelari.presencebot.telegram.operation.scenario.team.create;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import com.xelari.presencebot.telegram.operation.callback.CallbackType;
import com.xelari.presencebot.telegram.operation.callback.TelegramCallback;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@TelegramCallback(CallbackType.CREATE_TEAM)
@RequiredArgsConstructor
public class CreateTeamCallbackHandler implements CallbackHandler {

    private final DialogDispatcher dialogDispatcher;
    private final CreateTeamFinalDialogHandler createTeamFinalDialogHandler;

    @Override
    public SendMessage apply(Callback callback, Update update) {

        var chatId = getChatId(update);

        var message = new SendMessage(
                chatId.toString(),
                Constants.ENTER_TEAM_NAME_MESSAGE
        );

        dialogDispatcher.putHandler(chatId, createTeamFinalDialogHandler);
        return message;
    }

}
