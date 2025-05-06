package com.xelari.presencebot.application.exception.team;

import com.xelari.presencebot.application.exception.BaseException;

public class TeamAlreadyExistsException extends BaseException {
    public TeamAlreadyExistsException(String message) {
        super(message);
    }
}
