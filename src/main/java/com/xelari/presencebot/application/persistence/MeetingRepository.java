package com.xelari.presencebot.application.persistence;

import com.xelari.presencebot.domain.entity.meeting.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface MeetingRepository extends JpaRepository<Meeting, UUID> {

    List<Meeting> findByTeamIdIn(Set<UUID> teamIds);

    List<Meeting> findByTeamId(UUID teamId);

    @Query("""
            SELECT m FROM Meeting m
            WHERE m.scheduledTime BETWEEN :now AND :cutoff
            AND NOT EXISTS
            (
                SELECT 1
                FROM MeetingAlert a
                WHERE a.meeting = m
                AND a.wasSend = true
            )
            """)
    List<Meeting> findUpcomingMeetingsWithoutAlerts(
            @Param("now") LocalDateTime now,
            @Param("cutoff") LocalDateTime cutoff
    );

}
