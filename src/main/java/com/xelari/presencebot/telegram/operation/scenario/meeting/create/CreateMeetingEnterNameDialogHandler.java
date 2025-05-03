package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.xelari.presencebot.telegram.operation.dialog.DialogHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CreateMeetingEnterNameDialogHandler implements DialogHandler {

    @Override
    public SendMessage apply(Update update, long chatId) {
        return null;
    }

}
