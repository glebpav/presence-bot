package com.xelari.presencebot.telegram.operation.scenario.team.create;

import com.xelari.presencebot.application.dto.team.CreateTeamRequest;
import com.xelari.presencebot.application.exception.InvalidInputException;
import com.xelari.presencebot.application.exception.TeamAlreadyExistsException;
import com.xelari.presencebot.application.exception.UserNotFoundException;
import com.xelari.presencebot.application.usecase.team.CreateTeamUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.UuidHandler;
import com.xelari.presencebot.telegram.operation.command.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CreateTeamCommand implements CommandHandler {

    @Override
    public SendMessage apply(Update update) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());

        return sendMessage;
    }
}
