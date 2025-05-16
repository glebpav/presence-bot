package com.xelari.presencebot.telegram.operation.scenario.meeting.event.remind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.dto.attendance.AttendanceRequest;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.*;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@TelegramCallback(CallbackType.ADD_REPORT_FOR_MEETING)
public class MeetingRemindAddReportCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final DialogDispatcher dialogDispatcher;
    private final DialogDataCache dialogDataCache;
    private final MeetingRemindAddReportFinalDialogHandler finalDialogHandler;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var attendanceRequest = callbackDataCache.getData(
                callback, AttendanceRequest.class
        );

        var message = new SendMessage(
                String.valueOf(getChatId(update)),
                Constants.WRITE_COMMENT_MESSAGE
        );

        dialogDispatcher.putHandler(getChatId(update), finalDialogHandler);
        dialogDataCache.putData(getChatId(update), attendanceRequest);

        return message;
    }

}
