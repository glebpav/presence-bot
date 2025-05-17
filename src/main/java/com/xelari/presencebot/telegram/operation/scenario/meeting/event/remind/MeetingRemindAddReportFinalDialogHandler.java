package com.xelari.presencebot.telegram.operation.scenario.meeting.event.remind;

import com.xelari.presencebot.application.adapter.dto.attendance.AttendanceReportRequest;
import com.xelari.presencebot.application.adapter.dto.attendance.AttendanceRequest;
import com.xelari.presencebot.application.exception.NotImplementedException;
import com.xelari.presencebot.application.usecase.attendance.SetAttendanceReportUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class MeetingRemindAddReportFinalDialogHandler implements DialogHandler {

    private final DialogDataCache dialogDataCache;
    private final SetAttendanceReportUseCase setAttendanceReportUseCase;

    @Override
    public SendMessage apply(Update update, long chatId) {

        var attendanceRequest = dialogDataCache.getData(chatId, AttendanceRequest.class);
        var reportText = getMessageText(update);

        var message = new SendMessage();
        message.setChatId(chatId);

        try {
            setAttendanceReportUseCase.execute(
                    new AttendanceReportRequest(
                            attendanceRequest,
                            reportText
                    )
            );
            message.setText(Constants.REPORT_WAS_SENT_SUCCESSFULLY_MESSAGE);
        } catch (NotImplementedException e) {
            message.setText(Constants.NOT_IMPLEMENTED_YET_MESSAGE);
        }

        dialogDataCache.removeData(chatId);
        return message;
    }
}
