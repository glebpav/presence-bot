package com.xelari.presencebot.application.usecase.meeting;

import com.xelari.presencebot.application.dto.meeting.CreateMeetingRequest;
import com.xelari.presencebot.application.exception.team.TeamNotFoundException;
import com.xelari.presencebot.application.persistence.MeetingRepository;
import com.xelari.presencebot.application.persistence.TeamRepository;
import com.xelari.presencebot.domain.entity.meeting.Meeting;
import com.xelari.presencebot.domain.entity.team.Team;
import com.xelari.presencebot.domain.valueobject.meeting.MeetingRepeat;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class CreateMeetingUseCase {

    private final MeetingRepository meetingRepository;
    private final TeamRepository teamRepository;

    // For attendance system implementation
    // private final UserRepository userRepository;
    // private final AttendanceRepository attendanceRepository;

    public List<Meeting> execute(CreateMeetingRequest request) {

        Team team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new TeamNotFoundException("Team not found"));

        return generateMeetings(request, team);
    }

    private List<Meeting> generateMeetings(CreateMeetingRequest request, Team team) {
        List<Meeting> meetings = new ArrayList<>();
        LocalDateTime nextMeetingTime = request.time();

        for (int i = 0; i < request.repeatCount(); i++) {
            System.out.println("Meeting name: " + request.name());
            Meeting meeting = createSingleMeeting(
                    request.name(),
                    request.description(),
                    team,
                    nextMeetingTime,
                    request.durationMinutes()
            );
            // TODO: check if there is already meeting with this time
            meetings.add(meeting);
            nextMeetingTime = calculateNextTime(nextMeetingTime, request.meetingRepeat());
        }

        return meetingRepository.saveAll(meetings);
    }

    private Meeting createSingleMeeting(
            String name,
            String description,
            Team team,
            LocalDateTime schedulesTime,
            int durationMinutes
    ) {

        var meeting = Meeting.builder()
                .name(name)
                .description(description)
                .team(team)
                .scheduledTime(schedulesTime)
                .durationMinutes(durationMinutes)
                .build();

        // TODO set attendance for TeamMembers

        return meeting;
    }

    private LocalDateTime calculateNextTime(LocalDateTime currentTime, MeetingRepeat repeat) {
        return switch (repeat) {
            case EVERY_DAY -> currentTime.plusDays(1);
            case EVERY_WEEK -> currentTime.plusWeeks(1);
            case EVERY_MONTH -> currentTime.plusMonths(1);
            case NONE -> currentTime;
        };
    }
}