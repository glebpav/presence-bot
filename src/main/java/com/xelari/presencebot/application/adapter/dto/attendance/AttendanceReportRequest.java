package com.xelari.presencebot.application.adapter.dto.attendance;

public record AttendanceReportRequest (
        AttendanceRequest attendanceRequest,
        String report
) { }
