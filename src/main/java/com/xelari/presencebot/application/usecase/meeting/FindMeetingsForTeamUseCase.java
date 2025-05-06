package com.xelari.presencebot.application.usecase.meeting;

import com.xelari.presencebot.application.exception.meeting.MeetingsNotFoundException;
import com.xelari.presencebot.application.persistence.MeetingRepository;
import com.xelari.presencebot.application.persistence.TeamRepository;
import com.xelari.presencebot.domain.entity.meeting.Meeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindMeetingsForTeamUseCase {

    private final MeetingRepository meetingRepository;

    public List<Meeting> execute(UUID teamId) {

        var meeting = meetingRepository.findByTeamId(teamId);

        if (meeting.isEmpty()) {
            throw new MeetingsNotFoundException();
        }

        return meeting;

    }

}
