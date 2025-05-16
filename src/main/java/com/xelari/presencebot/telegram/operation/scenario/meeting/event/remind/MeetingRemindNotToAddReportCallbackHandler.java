package com.xelari.presencebot.telegram.operation.scenario.meeting.event.remind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.operation.callback.*;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@TelegramCallback(CallbackType.NOT_TO_ADD_REPORT_FOR_MEETING)
public class MeetingRemindNotToAddReportCallbackHandler implements CallbackHandler {

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        return new SendMessage(
                String.valueOf(getChatId(update)),
                Constants.WILL_BE_WAITING_NEXT_TIME_MESSAGE
        );

    }

}
