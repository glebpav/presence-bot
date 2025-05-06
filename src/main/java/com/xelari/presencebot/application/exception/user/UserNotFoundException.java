package com.xelari.presencebot.application.exception.user;


import com.xelari.presencebot.application.exception.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super("User not found");
    }
}
