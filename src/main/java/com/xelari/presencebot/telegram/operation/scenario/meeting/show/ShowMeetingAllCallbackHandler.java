package com.xelari.presencebot.telegram.operation.scenario.meeting.show;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.exception.MeetingsNotFoundException;
import com.xelari.presencebot.application.exception.UserNotFoundException;
import com.xelari.presencebot.application.usecase.meeting.GetAllMeetingsForUserUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.UuidHandler;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackDataCache;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class ShowMeetingAllCallbackHandler implements CallbackHandler {

    private final GetAllMeetingsForUserUseCase getAllMeetingsForUserUseCase;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var userId = UuidHandler.longToUUID(update.getCallbackQuery().getFrom().getId());
        var chatId = update.getCallbackQuery().getMessage().getChatId();

        var message = new SendMessage();
        message.setChatId(chatId);

        try {
            var meeting = getAllMeetingsForUserUseCase.execute(userId);
            message.setText(Constants.FOUND_MEETING_MESSAGE(meeting));
        } catch (UserNotFoundException e) {
            message.setText(Constants.USER_NOT_FOUND_MESSAGE);
        } catch (MeetingsNotFoundException e) {
            message.setText(Constants.NO_MEETING_WAS_FOUND_MESSAGE);
        }

        return message;

    }
}
