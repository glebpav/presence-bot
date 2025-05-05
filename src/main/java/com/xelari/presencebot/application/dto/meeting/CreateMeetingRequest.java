package com.xelari.presencebot.application.dto.meeting;

import com.xelari.presencebot.domain.valueobject.meeting.MeetingRepeat;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@With
public record CreateMeetingRequest(
    UUID teamId,
    String name,
    String description,
    LocalDateTime time,
    MeetingRepeat meetingRepeat,
    int repeatCount,
    int durationMinutes
) { }
