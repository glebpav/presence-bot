package com.xelari.presencebot.telegram.operation.scenario.meeting.edit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.usecase.team.FindManagingTeamsUseCase;
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
@TelegramCallback(CallbackType.CHANGE_MEETING)
public class EditMeetingSelectTeamCallbackHandler implements CallbackHandler {

    private final FindManagingTeamsUseCase findManagingTeamsUseCase;
    private final ButtonBuilder buttonBuilder;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var userId = getUserId(update);
        var chatId = getChatId(update);

        var message = new SendMessage(
                chatId.toString(),
                Constants.SELECT_TEAM_FOR_MEETING_MESSAGE
        );

        var teams = findManagingTeamsUseCase.execute(userId);
        addButtons(message, teams);

        return message;
    }

    private void addButtons(SendMessage message, List<Team> teams) {
        buttonBuilder.addVerticalKeyboard(
                message,
                teams.stream()
                        .map(team ->
                                new ButtonDescription(
                                        team.getName(),
                                        CallbackType.CHANGE_MEETING_SELECT_MEETING,
                                        team.getId()
                                )
                        ).toList()
        );
    }

}
