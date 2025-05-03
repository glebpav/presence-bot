package com.xelari.presencebot.application.persistence;

import com.xelari.presencebot.domain.entity.InvitationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvitationTokenRepository extends JpaRepository<InvitationToken, UUID> {

    Optional<InvitationToken> findByToken(String token);

}
