package com.xelari.presencebot.application.adapter.dto.user;

import java.util.UUID;

public record UserCreationRequest(
        String name,
        String secondName,
        UUID id,
        Long backConnection
) { }
