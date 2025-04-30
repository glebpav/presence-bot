package com.xelari.presencebot.application.persistence;

import com.xelari.presencebot.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
