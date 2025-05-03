package com.xelari.presencebot.application.usecase.team;

import com.xelari.presencebot.application.dto.team.CreateTeamRequest;
import com.xelari.presencebot.application.exception.TeamAlreadyExistsException;
import com.xelari.presencebot.application.exception.UserNotFoundException;
import com.xelari.presencebot.application.persistence.TeamRepository;
import com.xelari.presencebot.application.persistence.UserRepository;
import com.xelari.presencebot.domain.entity.team.Team;
import com.xelari.presencebot.domain.entity.team.TeamMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateTeamUseCase {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public void execute(CreateTeamRequest createTeamRequest) throws TeamAlreadyExistsException {

        var user = userRepository
                .findById(createTeamRequest.userId())
                .orElseThrow(UserNotFoundException::new);

        teamRepository
                .findByName(createTeamRequest.name())
                .ifPresent(
                        (it) -> {
                            throw new TeamAlreadyExistsException(
                                    String.format("Team with name '%s' already exists", it.getName())
                            );
                        }
                );

        var teamMember = TeamMember.builder()
                .user(user)
                .role(TeamMember.Role.MANAGER)
                .build();

        var team = Team.builder()
                .name(createTeamRequest.name())
                .members(Set.of(teamMember))
                .build();

        teamMember.setTeam(team);
        teamRepository.save(team);

    }

}
