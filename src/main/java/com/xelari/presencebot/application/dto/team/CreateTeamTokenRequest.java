package com.xelari.presencebot.application.dto.team;

import com.xelari.presencebot.domain.entity.team.TeamMember;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateTeamTokenRequest(
        UUID teamId,
        TeamMember.Role role,
        LocalDateTime expireAt
) { }
