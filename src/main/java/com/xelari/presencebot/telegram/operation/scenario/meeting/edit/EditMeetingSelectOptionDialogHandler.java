package com.xelari.presencebot.telegram.operation.scenario.meeting.edit;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.CallbackType;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import com.xelari.presencebot.telegram.ui.ButtonBuilder;
import com.xelari.presencebot.telegram.ui.ButtonDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EditMeetingSelectOptionDialogHandler implements DialogHandler {

    private final DialogDataCache dialogDataCache;
    private final DialogDispatcher dialogDispatcher;
    private final ButtonBuilder buttonBuilder;

    @Override
    public SendMessage apply(Update update, long chatId) {

        var meetingIdxString = update.getMessage().getText();
        var availableMeetings = dialogDataCache.getData(chatId, List.class);

        var message = new SendMessage();
        message.setChatId(chatId);

        if (meetingIdxString == null || meetingIdxString.isBlank()) {
            message.setText(Constants.INPUT_CANT_BE_BLANK_MESSAGE);
            return message;
        }

        int meetingIdx;

        try {
            meetingIdx = Integer.parseInt(meetingIdxString);
        } catch (NumberFormatException e) {
            message.setText(Constants.CANT_UNDERSTAND);
            return message;
        }

        if (meetingIdx < 0 || meetingIdx >= availableMeetings.size()) {
            message.setText(Constants.NO_MATCHING_MEETING_IN_LIST_MESSAGE);
            return message;
        }

        var meetingId = UUID.fromString((String) ((Map) availableMeetings.get(meetingIdx)).get("id"));
        addKeyboard(message, meetingId);

        dialogDispatcher.removeHandler(chatId);

        message.setText(Constants.SELECT_OPTION_MESSAGE);
        return message;
    }

    private void addKeyboard(SendMessage message, UUID meetingId) {

        buttonBuilder.addHorizontalKeyboard(
                message,
                List.of(
                        new ButtonDescription(
                                "Delete Meeting",
                                CallbackType.CHANGE_MEETING_DELETE,
                                meetingId
                        ),
                        new ButtonDescription(
                                "Change Time",
                                CallbackType.CHANGE_MEETING_CHANGE_TIME,
                                meetingId
                        )
                )
        );

    }
}
