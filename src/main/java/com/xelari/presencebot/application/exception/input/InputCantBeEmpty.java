package com.xelari.presencebot.application.exception.input;

import com.xelari.presencebot.application.exception.BaseException;

public class InputCantBeEmpty extends BaseException {
    public InputCantBeEmpty() {
        super("Input cannot be empty");
    }
}
