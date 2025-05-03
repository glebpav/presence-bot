package com.xelari.presencebot.application.usecase.meeting;

import com.xelari.presencebot.application.dto.meeting.CreateMeetingRequest;
import com.xelari.presencebot.application.exception.TeamNotFoundException;
import com.xelari.presencebot.application.persistence.MeetingRepository;
import com.xelari.presencebot.application.persistence.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateMeetingUseCase {

    private final MeetingRepository meetingRepository;
    private final TeamRepository teamRepository;

    public void Execute(CreateMeetingRequest createMeetingRequest) {

        var team = teamRepository
                .findById(createMeetingRequest.teamId())
                .orElseThrow(() -> new TeamNotFoundException("Team not found"));


    }

}
