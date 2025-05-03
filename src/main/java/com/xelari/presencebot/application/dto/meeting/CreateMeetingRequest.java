package com.xelari.presencebot.application.dto.meeting;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateMeetingRequest(
    UUID teamId,
    LocalDateTime time,
    int durationMinutes,
    String name,
    String description
) { }
