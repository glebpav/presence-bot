package com.xelari.presencebot.application.persistence;

import com.xelari.presencebot.domain.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamMemberRepository extends JpaRepository<TeamMember, UUID> {
}
