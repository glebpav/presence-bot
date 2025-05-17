package com.xelari.presencebot.application.adapter.dto.team;

import java.util.UUID;

public record CreateTeamRequest(
        UUID userId,
        String name
) { }
