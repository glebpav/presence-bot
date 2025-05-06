package com.xelari.presencebot.application.exception.user;

import com.xelari.presencebot.application.exception.BaseException;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
