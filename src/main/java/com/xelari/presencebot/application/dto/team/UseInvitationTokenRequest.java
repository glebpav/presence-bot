package com.xelari.presencebot.application.dto.team;

import java.util.UUID;

public record UseInvitationTokenRequest (
        String token,
        UUID userId
) { }
