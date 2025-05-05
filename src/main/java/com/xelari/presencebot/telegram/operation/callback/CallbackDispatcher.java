package com.xelari.presencebot.telegram.operation.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.JsonHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.create.CreateMeetingEnterNameCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.create.CreateMeetingEnterRepeatCountCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.create.CreateMeetingSelectTeamCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.team.create.CreateTeamCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.team.invite.InviteMemberGenerateTokenCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.team.invite.InviteMemberSelectRoleCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.team.invite.InviteMemberSelectTeamCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.team.join.JoinTeamCallbackHandler;
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
            @Autowired JoinTeamCallbackHandler enterTeamCallbackHandler,
            @Autowired CreateMeetingEnterNameCallbackHandler createMeetingEnterNameCallbackHandler,
            @Autowired CreateMeetingSelectTeamCallbackHandler createMeetingSelectTeamCallbackHandler,
            @Autowired CreateMeetingEnterRepeatCountCallbackHandler createMeetingEnterRepeatCountCallbackHandler
            ) {

        this.callbacks = Map.of(
                CallbackType.CREATE_TEAM, createTeamCallbackHandler,
                CallbackType.INVITE_MEMBER, inviteMemberSelectTeamCallbackHandler,
                CallbackType.INVITE_MEMBER_SELECT_ROLE, inviteMemberSelectRoleCallbackHandler,
                CallbackType.INVITE_MEMBER_GENERATE_TOKEN, inviteMemberGenerateTokenCallbackHandler,
                CallbackType.ENTER_TEAM, enterTeamCallbackHandler,
                CallbackType.CREATE_MEETING_SELECT_TEAM, createMeetingSelectTeamCallbackHandler,
                CallbackType.CREATE_MEETING_TEAM_SELECTED, createMeetingEnterNameCallbackHandler,
                CallbackType.CREATE_MEETING_REPEAT_SELECTED, createMeetingEnterRepeatCountCallbackHandler
        );

    }

    public SendMessage handleCallbacks(Update update) throws JsonProcessingException {

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
