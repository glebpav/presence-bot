package com.xelari.presencebot.application.exception.meeting;

import com.xelari.presencebot.application.exception.BaseException;

public class MeetingsNotFoundException extends BaseException {
    public MeetingsNotFoundException() {
        super("Meetings not found");
    }
}
