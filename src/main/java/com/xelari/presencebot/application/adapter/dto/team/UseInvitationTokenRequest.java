package com.xelari.presencebot.application.adapter.dto.team;

import java.util.UUID;

public record UseInvitationTokenRequest (
        String token,
        UUID userId
) { }
