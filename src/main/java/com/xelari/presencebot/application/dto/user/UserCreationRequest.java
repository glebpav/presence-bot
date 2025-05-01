package com.xelari.presencebot.application.dto.user;

import java.util.UUID;

public record UserCreationRequest(
        String name,
        String secondName,
        UUID id,
        Long backConnection
) { }
