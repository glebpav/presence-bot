package com.xelari.presencebot.application.dto.meeting;

import com.xelari.presencebot.domain.entity.meeting.Meeting;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MeetingResponse(
        UUID id,
        String name,
        String description,
        LocalDateTime scheduledTime,
        String teamName,
        int durationMinutes
) {

    @Override
    public String toString() {
        return "Name: " + name + ", Description: " + description + ", Scheduled Time: " + scheduledTime + ", Team: " + teamName;
    }

    public static MeetingResponse fromMeeting(Meeting meeting) {
        return new MeetingResponse(
                meeting.getId(),
                meeting.getName(),
                meeting.getDescription(),
                meeting.getScheduledTime(),
                meeting.getTeam().getName(),
                meeting.getDurationMinutes()
        );
    }

    public static List<MeetingResponse> fromMeetingList(List<Meeting> meetings) {
        return meetings.stream()
                .map(MeetingResponse::fromMeeting)
                .toList();
    }

}
