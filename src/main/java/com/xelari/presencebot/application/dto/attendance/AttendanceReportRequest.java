package com.xelari.presencebot.application.dto.attendance;

public record AttendanceReportRequest (
        AttendanceRequest attendanceRequest,
        String report
) { }
