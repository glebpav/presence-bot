package com.xelari.presencebot.application.usecase.meeting.event.remind;

import com.xelari.presencebot.application.adapter.dto.meeting.MeetingResponse;
import com.xelari.presencebot.application.persistence.MeetingAlertRepository;
import com.xelari.presencebot.application.persistence.MeetingRepository;
import com.xelari.presencebot.domain.entity.meeting.MeetingAlert;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Transactional
@RequiredArgsConstructor
public class RemindMeetingUseCase {

    @Setter
    OnRemindMeetingListener onRemindMeetingListener;

    private final MeetingRepository meetingRepository;
    private final MeetingReminderConfig meetingReminderConfig;
    private final MeetingAlertRepository meetingAlertRepository;

    @Scheduled(fixedRateString = "${meeting.reminder.check-interval-ms}")
    public void checkRemind() {

        System.out.println("scheduled meeting reminder");

        var meetings = meetingRepository.findUpcomingMeetingsWithoutAlerts(
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(
                        meetingReminderConfig.getNotificationIntervalMinutes()
                )
        );

        System.out.println("scheduled meeting reminder found " + meetings.size() + " meetings");

        meetings.forEach(meeting -> {

            var meetingResponse = MeetingResponse.fromMeeting(meeting);

            meeting.getTeam().getMembers().forEach(teamMember -> {
                onRemindMeetingListener.onRemind(
                        teamMember.getUser().getBackConnection(),
                        meetingResponse
                );
            });

            var meetingAlert = meetingAlertRepository
                    .findByMeeting(meeting)
                    .orElse(new MeetingAlert(meeting, true));

            meetingAlert.setWasSend(true);
            meetingAlertRepository.save(meetingAlert);

        });
    }

    public interface OnRemindMeetingListener {
        void onRemind(Long backConnection, MeetingResponse meetingResponse);
    }

}
