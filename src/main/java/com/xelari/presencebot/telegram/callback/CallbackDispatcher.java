package com.xelari.presencebot.telegram.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.JsonHandler;
import com.xelari.presencebot.telegram.callback.handler.team.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CallbackDispatcher {

    private final Map<CallbackType, CallbackHandler> callbacks;

    public CallbackDispatcher(
            @Autowired CreateTeamCallbackHandler createTeamCallbackHandler,
            @Autowired InviteMemberSelectTeamCallbackHandler inviteMemberSelectTeamCallbackHandler,
            @Autowired InviteMemberSelectRoleCallbackHandler inviteMemberSelectRoleCallbackHandler,
            @Autowired InviteMemberGenerateTokenCallbackHandler inviteMemberGenerateTokenCallbackHandler,
            @Autowired JoinTeamCallbackHandler enterTeamCallbackHandler
            ) {
        // TODO: fill callback map
        this.callbacks = Map.of(
                CallbackType.CREATE_TEAM, createTeamCallbackHandler,
                CallbackType.INVITE_MEMBER, inviteMemberSelectTeamCallbackHandler,
                CallbackType.SELECT_ROLE_FOR_INVITATION, inviteMemberSelectRoleCallbackHandler,
                CallbackType.GENERATE_TOKEN_FOR_INVITATION, inviteMemberGenerateTokenCallbackHandler,
                CallbackType.ENTER_TEAM, enterTeamCallbackHandler
        );

    }

    public SendMessage handleCallbacks(Update update) throws JsonProcessingException {
        System.out.println("Gotten text: " + update.getCallbackQuery().getData());

        String stringData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        SendMessage answer;
        if (stringData.isEmpty()) {
            answer = new SendMessage(String.valueOf(chatId), Constants.ERROR);
        } else {

            Callback callback;
            try {
                callback = JsonHandler.fromJson(stringData);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            CallbackHandler callbackBiFunction = callbacks.get(callback.getCallbackType());
            answer = callbackBiFunction.apply(callback, update);
        }

        return answer;
    }



}
