package com.xelari.presencebot.application.dto.attendance;

import java.util.UUID;

public record AttendanceRequest (
        UUID meetingId,
        UUID userId,
        Boolean isAttending
) { }
