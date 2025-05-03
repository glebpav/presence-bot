package com.xelari.presencebot.application.persistence;

import com.xelari.presencebot.domain.entity.meeting.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MeetingRepository extends JpaRepository<Meeting, UUID> {
}
