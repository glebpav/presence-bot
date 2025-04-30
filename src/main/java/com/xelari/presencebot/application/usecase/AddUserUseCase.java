package com.xelari.presencebot.application.usecase;

import com.xelari.presencebot.application.dto.user.UserCreationRequest;
import com.xelari.presencebot.application.exception.UserAlreadyExistsException;
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
                .findByNameAndSecondName(
                        userCreationRequest.name(),
                        userCreationRequest.secondName()
                ).ifPresentOrElse(
                        (it) -> {
                            throw new UserAlreadyExistsException(
                                    "User with this name already exists"
                            );
                        },
                        () -> userRepository.save(
                                new User(
                                        userCreationRequest.name(),
                                        userCreationRequest.secondName(),
                                        userCreationRequest.backConnection()
                                )
                        )
                );
    }

}
