package com.xelari.presencebot.application.exception;


public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super("User not found");
    }
}
