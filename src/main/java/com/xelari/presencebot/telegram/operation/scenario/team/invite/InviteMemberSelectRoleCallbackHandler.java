package com.xelari.presencebot.telegram.operation.scenario.team.invite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.application.dto.team.CreateTeamTokenRequest;
import com.xelari.presencebot.domain.entity.team.TeamMember;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.config.InvitationTokenConfig;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackDataCache;
import com.xelari.presencebot.telegram.operation.callback.CallbackHandler;
import com.xelari.presencebot.telegram.operation.callback.CallbackType;
import com.xelari.presencebot.telegram.ui.ButtonBuilder;
import com.xelari.presencebot.telegram.ui.ButtonDescription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InviteMemberSelectRoleCallbackHandler implements CallbackHandler {

    private final CallbackDataCache callbackDataCache;
    private final InvitationTokenConfig invitationTokenConfig;
    private final ButtonBuilder buttonBuilder;

    @Override
    public SendMessage apply(Callback callback, Update update) throws JsonProcessingException {

        var teamId = callbackDataCache.getData(callback, UUID.class);

        SendMessage message = new SendMessage();
        message.setChatId(update.getCallbackQuery().getMessage().getChatId());
        message.setText(Constants.SELECT_ROLE_MESSAGE);

        addKeyboard(message, List.of(TeamMember.Role.values()), teamId);

        return message;
    }

    private void addKeyboard(SendMessage sendMessage, List<TeamMember.Role> availableRole, UUID teamId) throws JsonProcessingException {

        var request = CreateTeamTokenRequest
                .builder()
                .teamId(teamId)
                .expireAt(LocalDateTime.now().plusDays(invitationTokenConfig.getExpirationDays()))
                .build();

        buttonBuilder.addHorizontalKeyboard(
                sendMessage,
                availableRole.stream().map(
                        role -> new ButtonDescription(
                                role.toString(),
                                CallbackType.INVITE_MEMBER_GENERATE_TOKEN,
                                request.withRole(role)
                        )
                ).toList()
        );

    }

}
