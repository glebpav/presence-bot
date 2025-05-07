package com.xelari.presencebot.application.usecase.meeting;

import com.xelari.presencebot.application.dto.meeting.ChangeMeetingTimeRequest;
import com.xelari.presencebot.application.exception.meeting.MeetingsNotFoundException;
import com.xelari.presencebot.application.persistence.MeetingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
public class ChangeMeetingTimeUseCase {

    private final MeetingRepository meetingRepository;

    public void execute(ChangeMeetingTimeRequest changeMeetingTimeRequest) {

        var meeting = meetingRepository
                .findById(changeMeetingTimeRequest.meetingId())
                .orElseThrow(MeetingsNotFoundException::new);

        meeting.setScheduledTime(
                changeMeetingTimeRequest.startDateTime()
        );

        meetingRepository.save(meeting);
    }

}
