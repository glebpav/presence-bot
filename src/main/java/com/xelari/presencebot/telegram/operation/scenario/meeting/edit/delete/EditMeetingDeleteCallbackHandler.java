package com.xelari.presencebot.telegram.operation.scenario.meeting.edit.delete;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.usecase.meeting.DeleteMeetingUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.*;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

@RequiredArgsConstructor
@TelegramCallback(CallbackType.CHANGE_MEETING_DELETE)
public class EditMeetingDeleteCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final DeleteMeetingUseCase deleteMeetingUseCase;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var chatId = getChatId(update);
        var meetingId = callbackDataCache.getData(callback, UUID.class);

        var message = new SendMessage();
        message.setChatId(chatId);

        deleteMeetingUseCase.execute(meetingId);

        message.setText(Constants.MEETING_DELETED_SUCCESSFULLY_MESSAGE);
        return message;
    }
}

