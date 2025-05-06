package com.xelari.presencebot.application.usecase.team;

import com.xelari.presencebot.application.exception.team.TeamNotFoundException;
import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.persistence.TeamRepository;
import com.xelari.presencebot.application.persistence.UserRepository;
import com.xelari.presencebot.domain.entity.team.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindBelongedTeamsUseCase {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public List<Team> execute(UUID userId) {

        userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);

        var teams = teamRepository.findAllTeamsByUser(userId);

        if (teams.isEmpty()) {
            throw new TeamNotFoundException("User doesn't belong to any team");
        }

        return teams;
    }

}
