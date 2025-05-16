package com.xelari.presencebot.application.persistence;

import com.xelari.presencebot.domain.entity.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    Optional<Attendance> findByMeetingIdAndUserId(UUID meetingId, UUID userId);

}
