package com.xelari.presencebot.telegram.ui;

import com.xelari.presencebot.telegram.operation.callback.CallbackType;

public record ButtonDescription (
        String text,
        CallbackType callbackType,
        Object data
) { }
