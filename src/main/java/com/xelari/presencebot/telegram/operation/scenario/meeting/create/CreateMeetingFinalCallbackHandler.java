package com.xelari.presencebot.telegram.operation.scenario.meeting.create;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.dto.meeting.CreateMeetingRequest;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackDataCache;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CreateMeetingFinalCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    // private final CreateMeeting

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var request = callbackDataCache.getData(callback, CreateMeetingRequest.class);



        return null;
    }

}
