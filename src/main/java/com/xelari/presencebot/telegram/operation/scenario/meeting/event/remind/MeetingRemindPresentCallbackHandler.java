package com.xelari.presencebot.telegram.operation.scenario.meeting.event.remind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.adapter.dto.attendance.AttendanceRequest;
import com.xelari.presencebot.application.exception.meeting.MeetingsNotFoundException;
import com.xelari.presencebot.application.exception.meeting.UserNotInvitedForMeetingException;
import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.usecase.attendance.SetAttendanceUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.*;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

@RequiredArgsConstructor
@TelegramCallback(CallbackType.WILL_PRESENT)
public class MeetingRemindPresentCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final SetAttendanceUseCase setAttendanceUseCase;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var meetingId = callbackDataCache.getData(callback, UUID.class);
        var userId = getUserId(update);

        var attendanceRequest = new AttendanceRequest(
                meetingId,
                userId,
                true
        );

        var message = new SendMessage();
        message.setChatId(getChatId(update));

        try {
            setAttendanceUseCase.execute(attendanceRequest);
            message.setText(Constants.THANKS_FOR_ATTENDANCE_MESSAGE);
        } catch (UserNotFoundException e) {
            message.setText(Constants.USER_NOT_FOUND_MESSAGE);
        } catch (MeetingsNotFoundException e) {
            message.setText(Constants.NO_MEETING_WAS_FOUND_MESSAGE);
        } catch (UserNotInvitedForMeetingException e) {
            message.setText(Constants.USER_NOT_INVITED_FOR_MEETING_MESSAGE);
        }

        return message;
    }

}
