package com.xelari.presencebot.telegram.operation.scenario.meeting.show;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.exception.meeting.MeetingsNotFoundException;
import com.xelari.presencebot.application.usecase.meeting.FindMeetingsForTeamUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.*;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

@RequiredArgsConstructor
@TelegramCallback(CallbackType.SHOW_MEETINGS_WITH_SELECTED_TEAM)
public class ShowMeetingForTeamCallbackHandler implements CallbackHandler {

    private final FindMeetingsForTeamUseCase findMeetingsForTeamUseCase;
    private final CallbackDataCache callbackDataCache;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var teamId = callbackDataCache.getData(callback, UUID.class);

        var message = new SendMessage();
        message.setChatId(getChatId(update));

        try {
            var meetings = findMeetingsForTeamUseCase.execute(teamId);
            message.setText(Constants.FOUND_MEETING_MESSAGE(meetings));
        } catch (MeetingsNotFoundException e) {
            message.setText(Constants.NO_MEETING_WAS_FOUND_MESSAGE);
            return message;
        }

        return message;
    }

}
