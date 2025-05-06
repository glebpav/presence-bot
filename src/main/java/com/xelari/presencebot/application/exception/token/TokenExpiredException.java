package com.xelari.presencebot.application.exception.token;

import com.xelari.presencebot.application.exception.BaseException;

public class TokenExpiredException extends BaseException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
