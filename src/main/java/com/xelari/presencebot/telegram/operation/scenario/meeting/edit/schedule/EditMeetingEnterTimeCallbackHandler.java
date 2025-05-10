package com.xelari.presencebot.telegram.operation.scenario.meeting.edit.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.*;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

@RequiredArgsConstructor
@TelegramCallback(CallbackType.CHANGE_MEETING_CHANGE_TIME)
public class EditMeetingEnterTimeCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final DialogDispatcher dialogDispatcher;
    private final DialogDataCache dialogDataCache;

    private final EditMeetingChangeTimeDialogHandler editMeetingChangeTimeDialogHandler;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var meetingId = callbackDataCache.getData(callback, UUID.class);
        var chatId = getChatId(update);

        var message = new SendMessage();
        message.setChatId(chatId);

        dialogDataCache.putData(chatId, meetingId);
        dialogDispatcher.putHandler(chatId, editMeetingChangeTimeDialogHandler);

        message.setText(Constants.INPUT_NEW_MEETING_TIME_MESSAGE);
        message.enableMarkdown(true);

        return message;
    }
}
