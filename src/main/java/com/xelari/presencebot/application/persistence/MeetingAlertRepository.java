package com.xelari.presencebot.application.persistence;

import com.xelari.presencebot.domain.entity.meeting.Meeting;
import com.xelari.presencebot.domain.entity.meeting.MeetingAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingAlertRepository extends JpaRepository<MeetingAlert, Long> {

    Optional<MeetingAlert> findByMeeting(Meeting meeting);

}
