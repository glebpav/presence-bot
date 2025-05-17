package com.xelari.presencebot.application.usecase.user;

import com.xelari.presencebot.application.adapter.dto.user.UserCreationRequest;
import com.xelari.presencebot.application.exception.user.UserAlreadyExistsException;
import com.xelari.presencebot.application.persistence.UserRepository;
import com.xelari.presencebot.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddUserUseCase {

    private final UserRepository userRepository;

    public void execute(UserCreationRequest userCreationRequest) throws UserAlreadyExistsException {
        userRepository
                .findById(userCreationRequest.id())
                .ifPresentOrElse(
                        (it) -> {
                            throw new UserAlreadyExistsException(
                                    "User with this name already exists"
                            );
                        },
                        () -> userRepository.save(
                                new User(
                                        userCreationRequest.id(),
                                        userCreationRequest.name(),
                                        userCreationRequest.secondName(),
                                        userCreationRequest.backConnection()
                                )
                        )
                );
    }

}
