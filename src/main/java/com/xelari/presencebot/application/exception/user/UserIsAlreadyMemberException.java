package com.xelari.presencebot.application.exception.user;

import com.xelari.presencebot.application.exception.BaseException;

public class UserIsAlreadyMemberException extends BaseException {
    public UserIsAlreadyMemberException(String message) {
        super(message);
    }
}
