package com.xelari.presencebot.telegram.operation.scenario.team.join;

import com.xelari.presencebot.application.dto.team.UseInvitationTokenRequest;
import com.xelari.presencebot.application.exception.token.TokenExpiredException;
import com.xelari.presencebot.application.exception.token.TokenNotFoundException;
import com.xelari.presencebot.application.exception.user.UserIsAlreadyMemberException;
import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.usecase.team.UseInvitationTokenUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.UuidHandler;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class JoinTeamDialogHandler implements DialogHandler {

    private final UseInvitationTokenUseCase useInvitationTokenUseCase;
    private final DialogDispatcher dialogDispatcher;

    @Override
    public SendMessage apply(Update update, long chatId) {

        var userId = UuidHandler.longToUUID(update.getMessage().getFrom().getId());
        var tokenString = update.getMessage().getText();

        var message = new SendMessage();
        message.enableHtml(true);
        message.setChatId(chatId);

        if (tokenString == null || tokenString.isBlank()) {
            message.setText(Constants.INPUT_CANT_BE_BLANK_MESSAGE);
            return message;
        }

        var useInvitationTokenRequest = new UseInvitationTokenRequest(
                tokenString.trim(),
                userId
        );

        try {
            var team = useInvitationTokenUseCase.execute(useInvitationTokenRequest);
            message.setText(Constants.SUCCESSFUL_JOINED_TEAM_MESSAGE(team));
            dialogDispatcher.removeHandler(chatId);
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
