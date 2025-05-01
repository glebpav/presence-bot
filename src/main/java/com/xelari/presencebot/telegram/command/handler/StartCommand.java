package com.xelari.presencebot.telegram.command.handler;

import com.xelari.presencebot.application.dto.user.UserCreationRequest;
import com.xelari.presencebot.application.exception.UserAlreadyExistsException;
import com.xelari.presencebot.application.usecase.AddUserUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.UuidHandler;
import com.xelari.presencebot.telegram.command.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StartCommand implements CommandHandler {

    private final AddUserUseCase addUserUseCase;

    @Override
    public SendMessage apply(Update update) {
        var userFirstName = update.getMessage().getFrom().getUserName();
        var userLastName = update.getMessage().getFrom().getLastName();
        var chatId = update.getMessage().getChatId();
        var userId = UuidHandler.longToUUID(update.getMessage().getFrom().getId());
        var userRequest = new UserCreationRequest(userFirstName, userLastName, userId, chatId);

        SendMessage repliedMessage = new SendMessage();
        repliedMessage.setChatId(chatId);

        var messageTextBuilder = new StringBuilder();
        messageTextBuilder
                .append(Constants.GREETING_MESSAGE)
                .append(Constants.SPACE_BETWEEN);

        try {
            addUserUseCase.execute(userRequest);
            messageTextBuilder.append(Constants.SUCCESSFULLY_ADDED_MESSAGE);
        } catch (UserAlreadyExistsException e) {
            messageTextBuilder.append(Constants.USER_ALREADY_EXISTS_MESSAGE);
        }

        repliedMessage.setText(messageTextBuilder.toString());
        return repliedMessage;
    }
}
