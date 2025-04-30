package com.xelari.presencebot.application.usecase.team;

import com.xelari.presencebot.application.dto.team.CreateTeamRequest;
import com.xelari.presencebot.application.exception.TeamAlreadyExistsException;
import com.xelari.presencebot.application.exception.UserNotFoundException;
import com.xelari.presencebot.application.persistence.TeamRepository;
import com.xelari.presencebot.application.persistence.UserRepository;
import com.xelari.presencebot.domain.entity.Team;
import com.xelari.presencebot.domain.entity.TeamMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTeamUserCase {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    void execute(CreateTeamRequest createTeamRequest) {

        var user = userRepository
                .findById(createTeamRequest.userId())
                .orElseThrow(
                        () -> new UserNotFoundException(
                                "User with id " + createTeamRequest.userId() + " not found"
                        )
                );

        teamRepository
                .findByName(createTeamRequest.name())
                .ifPresent(
                        (it) -> {
                            throw new TeamAlreadyExistsException(
                                    String.format("Team with name '%s' already exists", it.getName())
                            );
                        }
                );


        var team = Team.builder()
                .name(createTeamRequest.name())
                .build();

        var teamMember = TeamMember.builder()
                .user(user)
                .team(team)
                .role(TeamMember.Role.MANAGER)
                .build();

        team.getMembers().add(teamMember);
        teamRepository.save(team);

    }

}
