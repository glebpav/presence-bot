package com.xelari.presencebot.application.persistence;

import com.xelari.presencebot.domain.entity.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {

    Optional<Team> findByName(String name);

    @Query("SELECT DISTINCT t FROM Team t " +
            "JOIN t.members m " +
            "WHERE m.role = 'MANAGER' " +
            "AND m.user.id = :userId")
    List<Team> findAllTeamsManagedByUser(@Param("userId") UUID userId);

}
