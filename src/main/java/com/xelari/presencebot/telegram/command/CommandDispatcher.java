package com.xelari.presencebot.telegram.command;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.command.handler.HealthCommand;
import com.xelari.presencebot.telegram.command.handler.StartCommand;
import jakarta.inject.Inject;
import jakarta.persistence.Inheritance;
import lombok.RequiredArgsConstructor;
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


    public CommandDispatcher(@Autowired StartCommand startCommand) {
        this.commands = Map.of(
                "/health", new HealthCommand(),
                "/start", startCommand
        );
    }

    public SendMessage handleCommands(Update update) {
        String messageText = update.getMessage().getText();
        String command = messageText.split(" ")[0];
        long chatId = update.getMessage().getChatId();

        var commandHandler = commands.get(command);
        if (commandHandler != null) {
            return commandHandler.apply(update);
        } else {
            return new SendMessage(String.valueOf(chatId), Constants.UNKNOWN_COMMAND);
        }
    }

}
