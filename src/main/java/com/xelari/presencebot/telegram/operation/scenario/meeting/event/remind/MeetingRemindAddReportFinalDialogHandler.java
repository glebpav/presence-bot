package com.xelari.presencebot.telegram.operation.scenario.meeting.event.remind;

import com.xelari.presencebot.application.adapter.dto.attendance.AttendanceReportRequest;
import com.xelari.presencebot.application.adapter.dto.attendance.AttendanceRequest;
import com.xelari.presencebot.application.adapter.file.FileStorageBoundary;
import com.xelari.presencebot.application.exception.NotImplementedException;
import com.xelari.presencebot.application.usecase.attendance.SetAttendanceReportUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.TelegramBot;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MeetingRemindAddReportFinalDialogHandler implements DialogHandler {

    @Lazy
    @Autowired
    private TelegramBot telegramBot;

    private final FileStorageBoundary fileStorageBoundary;
    private final DialogDataCache dialogDataCache;
    private final SetAttendanceReportUseCase setAttendanceReportUseCase;

    @Override
    public SendMessage apply(Update update, long chatId) {

        System.out.println("here");
        log.debug("In MeetingRemindAddReportFinalDialogHandler");

        var attendanceRequest = dialogDataCache.getData(chatId, AttendanceRequest.class);
        var reportText = getMessageText(update);

        log.info("hasDocument: " + update.getMessage().hasDocument());

        if (update.getMessage().hasDocument()) {
            var document = update.getMessage().getDocument();

            try (var fileInputStream = telegramBot.downloadTelegramFile(document.getFileId())) {

                fileStorageBoundary.uploadFile(
                        fileInputStream,
                        document.getFileName(),
                        document.getFileSize()
                );

            } catch (TelegramApiException | IOException e) {
                throw new RuntimeException(e);
            }

        }

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
