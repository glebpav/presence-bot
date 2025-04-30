package com.xelari.presencebot.application.dto.user;

public record UserCreationRequest(
        String name,
        String secondName,
        Long backConnection
) { }
