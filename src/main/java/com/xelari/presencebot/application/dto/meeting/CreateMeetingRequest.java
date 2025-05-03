package com.xelari.presencebot.application.dto.meeting;

import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@With
public record CreateMeetingRequest(
    UUID teamId,
    LocalDateTime time,
    int durationMinutes,
    String name,
    String description
) { }
