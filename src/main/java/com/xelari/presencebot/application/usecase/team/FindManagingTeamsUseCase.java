package com.xelari.presencebot.application.usecase.team;

import com.xelari.presencebot.application.exception.UserNotFoundException;
import com.xelari.presencebot.application.persistence.TeamRepository;
import com.xelari.presencebot.application.persistence.UserRepository;
import com.xelari.presencebot.domain.entity.team.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindManagingTeamsUseCase {

    private final TeamRepository repository;
    private final UserRepository userRepository;

    public List<Team> execute(UUID userId) {

        userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return repository.findAllTeamsManagedByUser(userId);
    }

}
