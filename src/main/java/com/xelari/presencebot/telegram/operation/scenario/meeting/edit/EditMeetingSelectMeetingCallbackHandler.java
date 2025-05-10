package com.xelari.presencebot.telegram.operation.scenario.meeting.edit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.dto.meeting.MeetingResponse;
import com.xelari.presencebot.application.exception.meeting.MeetingsNotFoundException;
import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.usecase.meeting.FindAllManagingMeetingsForUserUseCase;
import com.xelari.presencebot.application.usecase.meeting.FindMeetingsForTeamUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.*;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@TelegramCallback(CallbackType.CHANGE_MEETING_SELECT_MEETING)
public class EditMeetingSelectMeetingCallbackHandler implements CallbackHandler {

    private final FindMeetingsForTeamUseCase findMeetingsForTeamUseCase;
    private final EditMeetingSelectOptionDialogHandler editMeetingSelectOptionDialogHandler;

    private final CallbackDataCache callbackDataCache;
    private final DialogDataCache dialogDataCache;
    private final DialogDispatcher dialogDispatcher;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var userId = getUserId(update);
        var chatId = getChatId(update);
        var teamId = callbackDataCache.getData(callback, UUID.class);

        var message = new SendMessage();
        message.setChatId(chatId);

        List<MeetingResponse> meetings;

        try {
            meetings = findMeetingsForTeamUseCase.execute(teamId);
        } catch (MeetingsNotFoundException e) {
            message.setText(Constants.NO_MANAGING_MEETING_WAS_FOUND_MESSAGE);
            return message;
        }

        message.setText(Constants.FOUND_MEETING_MESSAGE(meetings));

        dialogDataCache.putData(chatId, meetings);
        dialogDispatcher.putHandler(
                chatId,
                editMeetingSelectOptionDialogHandler
        );

        return message;

    }
}
