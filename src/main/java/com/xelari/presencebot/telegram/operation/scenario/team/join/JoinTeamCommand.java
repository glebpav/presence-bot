package com.xelari.presencebot.telegram.operation.scenario.team.join;

import com.xelari.presencebot.application.dto.team.UseInvitationTokenRequest;
import com.xelari.presencebot.application.exception.*;
import com.xelari.presencebot.application.usecase.team.UseInvitationTokenUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.UuidHandler;
import com.xelari.presencebot.telegram.operation.command.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class JoinTeamCommand implements CommandHandler {

    private final UseInvitationTokenUseCase useInvitationTokenUseCase;

    @Override
    public SendMessage apply(Update update) {

        var userId = UuidHandler.longToUUID(update.getMessage().getFrom().getId());
        var chatId = update.getMessage().getChatId();

        var parsedInput = update.getMessage().getText().split(" ");
        if (parsedInput.length < 2) {
            throw new InvalidInputException();
        }

        var useInvitationTokenRequest = new UseInvitationTokenRequest(
                parsedInput[1],
                userId
        );

        var message = new SendMessage();
        message.enableHtml(true);
        message.setChatId(chatId);

        try {
            var team = useInvitationTokenUseCase.execute(useInvitationTokenRequest);
            message.setText(Constants.SUCCESSFUL_JOINED_TEAM_MESSAGE(team));
        } catch (UserNotFoundException e) {
            message.setText(Constants.USER_NOT_FOUND_MESSAGE);
        } catch (TokenNotFoundException e) {
            message.setText(Constants.TOKEN_NOT_EXISTS_MESSAGE);
        } catch (UserIsAlreadyMemberException e) {
            message.setText(Constants.ALREADY_IN_TEAM_MESSAGE);
        } catch (TokenExpiredException e) {
            message.setText(Constants.TOKEN_EXPIRED_MESSAGE);
        }

        return message;
    }
}
