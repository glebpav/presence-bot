package com.xelari.presencebot.telegram.operation.scenario.meeting.show;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.Constants;
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
public class ShowMeetingSelectTypeCallbackHandler implements CallbackHandler {

    private final ButtonBuilder buttonBuilder;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var chatId = getChatId(update);

        var message = new SendMessage(
                String.valueOf(chatId),
                Constants.SELECT_MEETING_SHOW_TYPE_MESSAGE
        );

        addKeyboard(message);

        return message;
    }

    private void addKeyboard(SendMessage message) {

        buttonBuilder.addHorizontalKeyboard(
                message,
                List.of(
                        new ButtonDescription(
                                "All Meetings",
                                CallbackType.SHOW_MEETINGS_ALL_MEETINGS,
                                ""
                        ),
                        new ButtonDescription(
                                "For Team",
                                CallbackType.SHOW_MEETINGS_FOR_TEAM,
                                ""
                        )
                )
        );

    }
}
