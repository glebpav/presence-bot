package com.xelari.presencebot.application.exception;

public class MeetingsNotFoundException extends BaseException {
    public MeetingsNotFoundException() {
        super("Meetings not found");
    }
}
