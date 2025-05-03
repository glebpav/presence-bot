package com.xelari.presencebot.telegram.operation.scenario.team;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.CallbackType;
import com.xelari.presencebot.telegram.operation.command.CommandHandler;
import com.xelari.presencebot.telegram.ui.ButtonBuilder;
import com.xelari.presencebot.telegram.ui.ButtonDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TeamCommand implements CommandHandler {

    private final ButtonBuilder buttonBuilder;

    List<ButtonDescription> buttonsDescription = List.of(
            new ButtonDescription("Create Team", CallbackType.CREATE_TEAM, 0),
            new ButtonDescription("Invite Member", CallbackType.INVITE_MEMBER, 0),
            new ButtonDescription("Enter Team", CallbackType.ENTER_TEAM, 0)
    );

    @Override
    public SendMessage apply(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(Constants.SELECT_OPTION_MESSAGE);
        buttonBuilder.addHorizontalKeyboard(message, buttonsDescription);
        return message;
    }

}
