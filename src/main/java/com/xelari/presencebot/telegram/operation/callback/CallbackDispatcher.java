package com.xelari.presencebot.telegram.operation.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.JsonHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.create.CreateMeetingEnterNameCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.create.CreateMeetingEnterRepeatCountCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.create.CreateMeetingSelectTeamCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.edit.EditMeetingDeleteCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.edit.EditMeetingSelectMeetingCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.edit.EditMeetingSelectTeamCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.show.ShowMeetingAllCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.show.ShowMeetingForTeamCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.show.ShowMeetingSelectTeamCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.meeting.show.ShowMeetingSelectTypeCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.team.create.CreateTeamCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.team.invite.InviteMemberGenerateTokenCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.team.invite.InviteMemberSelectRoleCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.team.invite.InviteMemberSelectTeamCallbackHandler;
import com.xelari.presencebot.telegram.operation.scenario.team.join.JoinTeamCallbackHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
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
            @Autowired CreateMeetingEnterRepeatCountCallbackHandler createMeetingEnterRepeatCountCallbackHandler,
            @Autowired ShowMeetingSelectTypeCallbackHandler showMeetingSelectTypeCallbackHandler,
            @Autowired ShowMeetingAllCallbackHandler showMeetingAllCallbackHandler,
            @Autowired ShowMeetingSelectTeamCallbackHandler showMeetingSelectTeamCallbackHandler,
            @Autowired ShowMeetingForTeamCallbackHandler showMeetingForTeamCallbackHandler,
            @Autowired EditMeetingSelectTeamCallbackHandler editMeetingSelectTeamCallbackHandler,
            @Autowired EditMeetingSelectMeetingCallbackHandler editMeetingSelectMeetingCallbackHandler,
            @Autowired EditMeetingDeleteCallbackHandler editMeetingDeleteCallbackHandler
            ) {

        callbacks = new HashMap<>();

        callbacks.put(CallbackType.CREATE_TEAM, createTeamCallbackHandler);
        callbacks.put(CallbackType.INVITE_MEMBER, inviteMemberSelectTeamCallbackHandler);
        callbacks.put(CallbackType.INVITE_MEMBER_SELECT_ROLE, inviteMemberSelectRoleCallbackHandler);
        callbacks.put(CallbackType.INVITE_MEMBER_GENERATE_TOKEN, inviteMemberGenerateTokenCallbackHandler);
        callbacks.put(CallbackType.ENTER_TEAM, enterTeamCallbackHandler);
        callbacks.put(CallbackType.CREATE_MEETING, createMeetingSelectTeamCallbackHandler);
        callbacks.put(CallbackType.CREATE_MEETING_TEAM_SELECTED, createMeetingEnterNameCallbackHandler);
        callbacks.put(CallbackType.CREATE_MEETING_REPEAT_SELECTED, createMeetingEnterRepeatCountCallbackHandler);
        callbacks.put(CallbackType.SHOW_MY_MEETINGS, showMeetingSelectTypeCallbackHandler);
        callbacks.put(CallbackType.SHOW_MEETINGS_ALL_MEETINGS, showMeetingAllCallbackHandler);
        callbacks.put(CallbackType.SHOW_MEETINGS_FOR_TEAM, showMeetingSelectTeamCallbackHandler);
        callbacks.put(CallbackType.SHOW_MEETINGS_WITH_SELECTED_TEAM, showMeetingForTeamCallbackHandler);
        callbacks.put(CallbackType.CHANGE_MEETING, editMeetingSelectTeamCallbackHandler);
        callbacks.put(CallbackType.CHANGE_MEETING_SELECT_MEETING, editMeetingSelectMeetingCallbackHandler);
        callbacks.put(CallbackType.CHANGE_MEETING_DELETE, editMeetingDeleteCallbackHandler);

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
