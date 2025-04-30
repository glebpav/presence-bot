package com.xelari.presencebot.application.dto.team;

import java.util.UUID;

public record CreateTeamRequest(
        UUID userId,
        String name
) { }
