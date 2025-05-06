package com.xelari.presencebot.telegram.operation.scenario.team.create;

import com.xelari.presencebot.application.dto.team.CreateTeamRequest;
import com.xelari.presencebot.application.exception.team.TeamAlreadyExistsException;
import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.usecase.team.CreateTeamUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.UuidHandler;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CreateTeamFinalDialogHandler implements DialogHandler {

    private final DialogDataCache dialogDataCache;
    private final DialogDispatcher dialogDispatcher;

    private final CreateTeamUseCase createTeamUseCase;

    @Override
    public SendMessage apply(Update update, long chatId) {

        var teamName = update.getMessage().getText();
        var userId = UuidHandler.longToUUID(update.getMessage().getFrom().getId());

        var message = new SendMessage();
        message.setChatId(chatId);

        if (teamName == null || teamName.isBlank()) {
            message.setText(Constants.INPUT_CANT_BE_BLANK_MESSAGE);
            return message;
        }

        var createTeamRequest = new CreateTeamRequest(userId, teamName);

        try {
            createTeamUseCase.execute(createTeamRequest);
            message.setText(Constants.TEAM_WAS_CREATED_MESSAGE);
        } catch (UserNotFoundException e) {
            message.setText(e.getMessage());
            return message;
        } catch (TeamAlreadyExistsException e) {
            message.setText(Constants.TEAM_ALREADY_EXISTS_MESSAGE);
            return message;
        }

        dialogDataCache.removeData(chatId);
        dialogDispatcher.removeHandler(chatId);

        return message;
    }

}
