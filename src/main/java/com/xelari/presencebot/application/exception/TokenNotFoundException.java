package com.xelari.presencebot.application.exception;

public class TokenNotFoundException extends BaseException {
    public TokenNotFoundException(String token) {
        super("Token not found: " + token);
    }
}
