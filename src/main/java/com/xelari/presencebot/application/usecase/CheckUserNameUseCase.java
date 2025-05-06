package com.xelari.presencebot.application.usecase;

import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CheckUserNameUseCase {

    public final UserRepository userRepository;

    public UUID execute(String userName) {
        var user =  userRepository
                .findByName(userName)
                .orElseThrow(UserNotFoundException::new);

        return user.getId();
    }

}
