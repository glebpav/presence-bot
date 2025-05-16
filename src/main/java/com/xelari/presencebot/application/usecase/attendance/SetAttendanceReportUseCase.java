package com.xelari.presencebot.application.usecase.attendance;

import com.xelari.presencebot.application.dto.attendance.AttendanceReportRequest;
import com.xelari.presencebot.application.exception.NotImplementedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class SetAttendanceReportUseCase {

    public void execute(AttendanceReportRequest attendanceReportRequest) {
        // TODO: implement this use case
        throw new NotImplementedException();
    }

}
