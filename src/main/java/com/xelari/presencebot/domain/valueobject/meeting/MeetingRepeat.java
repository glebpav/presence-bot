package com.xelari.presencebot.domain.valueobject.meeting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingRepeat {
    NONE("Without repeat"),
    EVERY_WEEK("Every week"),
    EVERY_DAY("Every day"),
    EVERY_MONTH("Every month");

    private final String description;
}
