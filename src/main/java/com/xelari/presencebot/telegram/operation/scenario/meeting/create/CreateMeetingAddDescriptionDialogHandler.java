package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.xelari.presencebot.application.dto.meeting.CreateMeetingRequest;
import com.xelari.presencebot.application.exception.InputCantBeEmpty;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.CallbackType;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import com.xelari.presencebot.telegram.ui.ButtonBuilder;
import com.xelari.presencebot.telegram.ui.ButtonDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateMeetingAddDescriptionDialogHandler implements DialogHandler {

    private final ButtonBuilder buttonBuilder;
    private final DialogDataCache dialogDataCache;

    @Override
    public SendMessage apply(Update update, long chatId) {

        var request = dialogDataCache.getData(chatId, CreateMeetingRequest.class);
        var meetingName = update.getMessage().getText();

        if (meetingName == null || meetingName.isEmpty()) {
            throw new InputCantBeEmpty();
        }

        request = request.withName(meetingName);

        var message = new SendMessage(
                String.valueOf(chatId),
                Constants.SHOULD_I_ADD_DESCRIPTION_MESSAGE
        );
        addButtons(message, request);

        return message;
    }

    private void addButtons(SendMessage message, CreateMeetingRequest request) {
        buttonBuilder.addHorizontalKeyboard(
                message,
                List.of(
                        new ButtonDescription(
                                "YES",
                                CallbackType.CREATE_MEETING_ADD_DESCRIPTION,
                                request
                        ),
                        new ButtonDescription(
                                "NO",
                                CallbackType.CREATE_MEETING_DONT_ADD_DESCRIPTION,
                                request
                        )
                )
        );
    }
}
