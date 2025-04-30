package com.xelari.presencebot.application.persistence;

import com.xelari.presencebot.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {

    Optional<Team> findByName(String name);

}
