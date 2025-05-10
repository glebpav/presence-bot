package com.xelari.presencebot.telegram.operation.scenario.meeting.show;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.exception.team.TeamNotFoundException;
import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.usecase.team.FindBelongedTeamsUseCase;
import com.xelari.presencebot.domain.entity.team.Team;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import com.xelari.presencebot.telegram.operation.callback.CallbackType;
import com.xelari.presencebot.telegram.operation.callback.TelegramCallback;
import com.xelari.presencebot.telegram.ui.ButtonBuilder;
import com.xelari.presencebot.telegram.ui.ButtonDescription;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@RequiredArgsConstructor
@TelegramCallback(CallbackType.SHOW_MEETINGS_FOR_TEAM)
public class ShowMeetingSelectTeamCallbackHandler implements CallbackHandler {

    private final FindBelongedTeamsUseCase findBelongedTeamsUseCase;
    private final ButtonBuilder buttonBuilder;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var userid = getUserId(update);
        var chatId = getChatId(update);

        var message = new SendMessage();
        message.setChatId(chatId);

        try {
            var teams = findBelongedTeamsUseCase.execute(userid);
            message.setText(Constants.SELECT_TEAM_MESSAGE);
            addKeyboard(message, teams);
        } catch (UserNotFoundException e) {
            message.setText(Constants.USER_NOT_FOUND_MESSAGE);
            return message;
        } catch (TeamNotFoundException e) {
            message.setText(Constants.NO_BELONG_TEAM_MESSAGE);
            return message;
        }

        return message;
    }

    private void addKeyboard(SendMessage message, List<Team> teams) {
        buttonBuilder.addVerticalKeyboard(
                message,
                teams.stream().map(team ->
                        new ButtonDescription(
                                team.getName(),
                                CallbackType.SHOW_MEETINGS_WITH_SELECTED_TEAM,
                                team.getId()
                        )
                ).toList()
        );
    }

}

