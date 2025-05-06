package com.xelari.presencebot.application.usecase.meeting;

import com.xelari.presencebot.application.exception.meeting.MeetingsNotFoundException;
import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.persistence.MeetingRepository;
import com.xelari.presencebot.application.persistence.TeamMemberRepository;
import com.xelari.presencebot.application.persistence.UserRepository;
import com.xelari.presencebot.domain.entity.meeting.Meeting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindAllMeetingsForUserUseCase {

    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;
    private final TeamMemberRepository teamMemberRepository;

    public List<Meeting> execute(UUID userId) {

        userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);

        var teamMembers = teamMemberRepository
                .findByUserId(userId)
                .stream()
                .map(teamMember -> teamMember.getTeam().getId())
                .collect(Collectors.toSet());

        var meetings = meetingRepository
                .findByTeamIdIn(teamMembers);

        if (meetings == null || meetings.isEmpty()) {
            throw new MeetingsNotFoundException();
        }

        return meetings;
    }

}
