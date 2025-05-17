package com.xelari.presencebot.application.adapter.dto.team;

import com.xelari.presencebot.domain.entity.team.TeamMember;
import lombok.Builder;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@With
public record CreateTeamTokenRequest(
        UUID teamId,
        TeamMember.Role role,
        LocalDateTime expireAt
) { }
