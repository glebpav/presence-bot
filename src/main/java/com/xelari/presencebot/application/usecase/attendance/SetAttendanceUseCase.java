package com.xelari.presencebot.application.usecase.attendance;

import com.xelari.presencebot.application.adapter.dto.attendance.AttendanceRequest;
import com.xelari.presencebot.application.exception.meeting.MeetingsNotFoundException;
import com.xelari.presencebot.application.exception.meeting.UserNotInvitedForMeetingException;
import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.persistence.AttendanceRepository;
import com.xelari.presencebot.application.persistence.MeetingRepository;
import com.xelari.presencebot.application.persistence.UserRepository;
import com.xelari.presencebot.domain.entity.attendance.Attendance;
import com.xelari.presencebot.domain.entity.team.TeamMember;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class SetAttendanceUseCase {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;

    public void execute(AttendanceRequest attendanceRequest) {

        var user = userRepository
                .findById(attendanceRequest.userId())
                .orElseThrow(UserNotFoundException::new);

        var meeting = meetingRepository
                .findById(attendanceRequest.meetingId())
                .orElseThrow(MeetingsNotFoundException::new);

        var isUserSteelInvited = meeting
                .getTeam()
                .getMembers()
                .stream()
                .map(TeamMember::getUser)
                .anyMatch(localeUser -> user.getId().equals(attendanceRequest.userId()));

        if (!isUserSteelInvited) {
            throw new UserNotInvitedForMeetingException();
        }

        attendanceRepository
                .findByMeetingIdAndUserId(
                        meeting.getId(),
                        user.getId()
                )
                .ifPresentOrElse(
                        attendance -> {
                            attendance.setAttending(attendanceRequest.isAttending());
                            attendanceRepository.save(attendance);
                        },
                        () -> attendanceRepository.save(
                                new Attendance(
                                        meeting,
                                        user,
                                        attendanceRequest.isAttending()
                                )
                        )
                );

    }

}
