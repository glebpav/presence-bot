package com.xelari.presencebot.application.usecase.meeting;

import com.xelari.presencebot.application.persistence.MeetingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class DeleteMeetingUseCase {

    private final MeetingRepository meetingRepository;

    public void execute(UUID meetingId) {
        meetingRepository.deleteById(meetingId);
    }

}
