package com.xelari.presencebot.application.exception.token;

import com.xelari.presencebot.application.exception.BaseException;

public class TokenNotFoundException extends BaseException {
    public TokenNotFoundException(String token) {
        super("Token not found: " + token);
    }
}
