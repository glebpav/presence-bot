package com.xelari.presencebot.application.exception.meeting;

import com.xelari.presencebot.application.exception.BaseException;

public class UserNotInvitedForMeetingException extends BaseException {
    public UserNotInvitedForMeetingException() {
        super("User is not invited for this meeting");
    }
}
