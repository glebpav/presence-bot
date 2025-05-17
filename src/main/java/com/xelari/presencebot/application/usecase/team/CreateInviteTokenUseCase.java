package com.xelari.presencebot.application.usecase.team;

import com.xelari.presencebot.application.adapter.dto.team.CreateTeamTokenRequest;
import com.xelari.presencebot.application.exception.team.TeamNotFoundException;
import com.xelari.presencebot.application.persistence.InvitationTokenRepository;
import com.xelari.presencebot.application.persistence.TeamRepository;
import com.xelari.presencebot.domain.entity.token.InvitationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateInviteTokenUseCase {

    private final InvitationTokenRepository tokenRepository;
    private final TeamRepository teamRepository;

    public InvitationToken execute(CreateTeamTokenRequest createTeamTokenRequest) {

        var team = teamRepository
                .findById(createTeamTokenRequest.teamId())
                .orElseThrow(() -> new TeamNotFoundException("Team is not found"));

        var token = InvitationToken.builder()
                .token(UUID.randomUUID().toString())
                .team(team)
                .assignedRole(createTeamTokenRequest.role())
                .expiresAt(createTeamTokenRequest.expireAt())
                .build();

        tokenRepository.save(token);

        return token;
    }

}
