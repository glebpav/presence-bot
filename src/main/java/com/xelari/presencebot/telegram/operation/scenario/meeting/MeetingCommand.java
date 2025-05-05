package com.xelari.presencebot.telegram.operation.scenario.meeting;

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
public class MeetingCommand implements CommandHandler {

    private final ButtonBuilder buttonBuilder;

    @Override
    public SendMessage apply(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(Constants.SELECT_OPTION_MESSAGE);

        addKeyboard(message);

        return message;
    }

    private void addKeyboard(SendMessage sendMessage) {

        buttonBuilder.addVerticalKeyboard(
                sendMessage,
                List.of(
                        new ButtonDescription(
                                "Create Meeting",
                                CallbackType.CREATE_MEETING,
                                ""
                        ),
                        new ButtonDescription(
                                "Edit Meeting",
                                CallbackType.CHANGE_MEETING,
                                ""
                        ),
                        new ButtonDescription(
                                "Show My Meetings",
                                CallbackType.SHOW_MY_MEETINGS,
                                ""
                        )
                )
        );


    }
}
