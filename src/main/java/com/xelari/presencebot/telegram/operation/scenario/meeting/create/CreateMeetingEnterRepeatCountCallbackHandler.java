package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.xelari.presencebot.application.dto.meeting.CreateMeetingRequest;
import com.xelari.presencebot.application.exception.team.TeamNotFoundException;
import com.xelari.presencebot.application.usecase.meeting.CreateMeetingUseCase;
import com.xelari.presencebot.domain.valueobject.meeting.MeetingRepeat;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackDataCache;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CreateMeetingEnterRepeatCountCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final DialogDispatcher dialogDispatcher;
    private final DialogDataCache dialogDataCache;

    private final CreateMeetingFinalDialogHandler createMeetingFinalDialogHandler;
    private final CreateMeetingUseCase createMeetingUseCase;

    @Override
    public SendMessage apply(Callback callback, Update update) {

        var chatId = update.getCallbackQuery().getMessage().getChatId();
        var request = callbackDataCache.getData(callback, CreateMeetingRequest.class);
        var message = new SendMessage();
        message.setChatId(chatId);

        if (request.meetingRepeat() == MeetingRepeat.NONE) {

            try {
                createMeetingUseCase.execute(request);
                message.setText(Constants.MEETING_CREATED_SUCCESSFULLY_MESSAGE);
            } catch (TeamNotFoundException e) {
                message.setText(e.getMessage());
            }
            return message;

        }

        dialogDataCache.putData(chatId, request);
        dialogDispatcher.putHandler(chatId, createMeetingFinalDialogHandler);

        message.setText(Constants.ENTER_MEETING_REPEAT_COUNT_MESSAGE);
        return message;
    }
}
