package com.xelari.presencebot.application.persistence;

import com.xelari.presencebot.domain.entity.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
}
