package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.xelari.presencebot.application.dto.meeting.CreateMeetingRequest;
import com.xelari.presencebot.application.exception.TeamNotFoundException;
import com.xelari.presencebot.application.usecase.meeting.CreateMeetingUseCase;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.dialog.DialogDataCache;
import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CreateMeetingFinalDialogHandler implements DialogHandler {

    private final DialogDataCache dialogDataCache;
    private final CreateMeetingUseCase createMeetingUseCase;

    @Override
    public SendMessage apply(Update update, long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        var inputText = update.getMessage().getText();
        if (inputText == null || inputText.trim().isEmpty()) {
            message.setText(Constants.CANT_UNDERSTAND);
            return message;
        }

        var repeatCount = Integer.parseInt(inputText);

        var request = dialogDataCache.getData(chatId, CreateMeetingRequest.class);
        request = request.withRepeatCount(repeatCount);

        try {
            createMeetingUseCase.execute(request);
            message.setText(Constants.MEETING_CREATED_SUCCESSFULLY_MESSAGE);
        } catch (TeamNotFoundException e) {
            message.setText(e.getMessage());
        }

        return message;
    }
}
