package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.xelari.presencebot.application.exception.user.UserNotFoundException;
import com.xelari.presencebot.application.usecase.team.FindManagingTeamsUseCase;
import com.xelari.presencebot.domain.entity.team.Team;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.UuidHandler;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import com.xelari.presencebot.telegram.operation.callback.CallbackType;
import com.xelari.presencebot.telegram.ui.ButtonBuilder;
import com.xelari.presencebot.telegram.ui.ButtonDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateMeetingSelectTeamCallbackHandler implements CallbackHandler {

    private final ButtonBuilder buttonBuilder;
    private final FindManagingTeamsUseCase findManagingTeamsUseCase;

    @Override
    public SendMessage apply(Callback callback, Update update) {

        var userId = getUserId(update);
        var chatId = getChatId(update);

        var message = new SendMessage();
        message.setChatId(chatId);

        try {
            var managingTeams = findManagingTeamsUseCase.execute(userId);

            if (managingTeams.isEmpty()) {
                message.setText(Constants.USER_HAVE_NOT_MANAGING_TEAMS_MESSAGE);
            } else {
                message.setText(Constants.SELECT_TEAM_MESSAGE);
                addKeyboard(message, managingTeams);
            }

        } catch (UserNotFoundException e) {
            message.setText(Constants.USER_NOT_FOUND_MESSAGE);
        }

        return message;
    }

    private void addKeyboard(SendMessage sendMessage, List<Team> managingTeams) {

        buttonBuilder.addVerticalKeyboard(
                sendMessage,
                managingTeams.stream().map(team ->
                        new ButtonDescription(
                                team.getName() + " [ " + team.getMembers().size() + " members ]",
                                CallbackType.CREATE_MEETING_TEAM_SELECTED,
                                team.getId()
                        )
                ).toList()
        );

    }

}
