package com.xelari.presencebot.application.adapter.dto.meeting;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChangeMeetingTimeRequest(
        UUID meetingId,
        LocalDateTime startDateTime
) { }
