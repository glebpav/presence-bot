package com.xelari.presencebot.telegram.operation.scenario.meeting.event.remind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.dto.attendance.AttendanceRequest;
import com.xelari.presencebot.application.exception.meeting.MeetingsNotFoundException;
import com.xelari.presencebot.application.exception.meeting.UserNotInvitedForMeetingException;
import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.usecase.attendance.SetAttendanceUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.*;
import com.xelari.presencebot.telegram.ui.ButtonBuilder;
import com.xelari.presencebot.telegram.ui.ButtonDescription;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@TelegramCallback(CallbackType.WILL_NOT_PRESENT)
public class MeetingRemindAbsentCallbackHandler implements CallbackHandler {

    private final ButtonBuilder buttonBuilder;
    private final CallbackDataCache callbackDataCache;
    private final SetAttendanceUseCase setAttendanceUseCase;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var meetingId = callbackDataCache.getData(callback, UUID.class);
        var userId = getUserId(update);

        var attendanceRequest = new AttendanceRequest(
                meetingId,
                userId,
                false
        );

        var message = new SendMessage();
        message.setChatId(getChatId(update));

        try {
            setAttendanceUseCase.execute(attendanceRequest);
            message.setText(Constants.ADD_COMMENT_MESSAGE);
            addKeyBoard(message, attendanceRequest);
        } catch (UserNotFoundException e) {
            message.setText(Constants.USER_NOT_FOUND_MESSAGE);
        } catch (MeetingsNotFoundException e) {
            message.setText(Constants.NO_MEETING_WAS_FOUND_MESSAGE);
        } catch (UserNotInvitedForMeetingException e) {
            message.setText(Constants.USER_NOT_INVITED_FOR_MEETING_MESSAGE);
        }

        return message;
    }

    private void addKeyBoard(SendMessage message, AttendanceRequest attendanceRequest) {
        buttonBuilder.addHorizontalKeyboard(
                message,
                List.of(
                        new ButtonDescription(
                                "Add",
                                CallbackType.ADD_REPORT_FOR_MEETING,
                                attendanceRequest
                        ),
                        new ButtonDescription(
                                "Not to add",
                                CallbackType.NOT_TO_ADD_REPORT_FOR_MEETING,
                                "none"
                        )
                )
        );
    }

}
