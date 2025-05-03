package com.xelari.presencebot.telegram.command;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.command.handler.HealthCommand;
import com.xelari.presencebot.telegram.command.handler.StartCommand;
import com.xelari.presencebot.telegram.command.handler.team.CreateTeamCommand;
import com.xelari.presencebot.telegram.command.handler.team.JoinTeamCommand;
import com.xelari.presencebot.telegram.command.handler.team.TeamCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@Slf4j
public class CommandDispatcher {

    private final Map<String, CommandHandler> commands;

    public CommandDispatcher(
            @Autowired HealthCommand healthCommand,
            @Autowired StartCommand startCommand,
            @Autowired TeamCommand teamCommand,
            @Autowired CreateTeamCommand createTeamCommand,
            @Autowired JoinTeamCommand joinTeamCommand
            ) {
        this.commands = Map.of(
                "/health", healthCommand,
                "/start", startCommand,
                "/team", teamCommand,
                "/create_team", createTeamCommand,
                "/join_team", joinTeamCommand
                // /team -> create (set user as manager + generate code) / join ()
        );
    }

    public SendMessage handleCommands(Update update) {
        String messageText = update.getMessage().getText();
        String command = messageText.split(" ")[0];
        long chatId = update.getMessage().getChatId();

        var commandHandler = commands.get(command);
        if (commandHandler != null) {
            // try {
                return commandHandler.apply(update);
            // }  catch (Exception e) {
            //     return new SendMessage(String.valueOf(chatId), e.getMessage());
            // }
        } else {
            return new SendMessage(String.valueOf(chatId), Constants.UNKNOWN_COMMAND);
        }
    }

}
