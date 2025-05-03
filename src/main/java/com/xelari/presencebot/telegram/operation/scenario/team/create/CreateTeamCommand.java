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

    private final CreateTeamUseCase createTeamUseCase;

    @Override
    public SendMessage apply(Update update) {

        var userId = UuidHandler.longToUUID(update.getMessage().getFrom().getId());

        var parsedInput = update.getMessage().getText().split(" ");
        if (parsedInput.length < 2) {
            throw new InvalidInputException();
        }
        var nameBuilder = new StringBuilder();
        for (int i = 1; i < parsedInput.length; i++) {
            nameBuilder.append(parsedInput[i]);
        }
        var teamName = nameBuilder.toString();

        var createTeamRequest = new CreateTeamRequest(userId, teamName);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());

        try {
            createTeamUseCase.execute(createTeamRequest);
            sendMessage.setText(Constants.TEAM_WAS_CREATED_MESSAGE);
        } catch (UserNotFoundException e) {
            sendMessage.setText(e.getMessage());
        } catch (TeamAlreadyExistsException e) {
            sendMessage.setText(Constants.TEAM_ALREADY_EXISTS_MESSAGE);
        }

        return sendMessage;
    }
}
