package com.xelari.presencebot.application.exception.input;

import com.xelari.presencebot.application.exception.BaseException;

public class InvalidInputException extends BaseException {
    public InvalidInputException() {
        super("Input is invalid");
    }
}
