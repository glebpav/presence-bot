package com.xelari.presencebot.telegram.operation.scenario.team.create;

import com.xelari.presencebot.telegram.operation.command.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CreateTeamCommand implements CommandHandler {

    @Override
    public SendMessage apply(Update update) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());

        return sendMessage;
    }
}
