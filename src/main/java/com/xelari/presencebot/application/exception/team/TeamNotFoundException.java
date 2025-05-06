package com.xelari.presencebot.application.exception.team;

import com.xelari.presencebot.application.exception.BaseException;

public class TeamNotFoundException extends BaseException {
    public TeamNotFoundException(String message) {
        super(message);
    }
}
