package com.xelari.presencebot.application.usecase.team;

import com.xelari.presencebot.application.dto.team.UseInvitationTokenRequest;
import com.xelari.presencebot.application.exception.TokenExpiredException;
import com.xelari.presencebot.application.exception.TokenNotFoundException;
import com.xelari.presencebot.application.exception.UserIsAlreadyMemberException;
import com.xelari.presencebot.application.exception.UserNotFoundException;
import com.xelari.presencebot.application.persistence.InvitationTokenRepository;
import com.xelari.presencebot.application.persistence.TeamRepository;
import com.xelari.presencebot.application.persistence.UserRepository;
import com.xelari.presencebot.domain.entity.Team;
import com.xelari.presencebot.domain.entity.TeamMember;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.beans.Transient;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UseInvitationTokenUseCase {

    private final InvitationTokenRepository invitationTokenRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public Team execute(UseInvitationTokenRequest useInvitationTokenRequest) {

        var user = userRepository
                .findById(useInvitationTokenRequest.userId())
                .orElseThrow(UserNotFoundException::new);

        var token = invitationTokenRepository
                .findByToken(useInvitationTokenRequest.token())
                .orElseThrow(() -> new TokenNotFoundException(useInvitationTokenRequest.token()));

        if (LocalDateTime.now().isAfter(token.getExpiresAt())) {
            throw new TokenExpiredException("Your token has expired");
        }

        token.getTeam().getMembers().forEach(member -> {
            if (member.getUser().getId().equals(user.getId())) {
                throw new UserIsAlreadyMemberException("You are already member of this team");
            }
        });

        token.getTeam().getMembers().add(
                new TeamMember(user, token.getTeam(), token.getAssignedRole())
        );

        return teamRepository.save(token.getTeam());
    }

}
