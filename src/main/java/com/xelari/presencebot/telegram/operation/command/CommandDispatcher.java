package com.xelari.presencebot.telegram.operation.command;

import com.xelari.presencebot.telegram.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CommandDispatcher {

    private final Map<String, CommandHandler> commands;


    public CommandDispatcher(List<CommandHandler> allCommands) {
        commands = allCommands
                .stream()
                .filter(handler -> handler.getClass().isAnnotationPresent(TelegramCommand.class))
                .collect(Collectors.toMap(
                        handler -> handler.getClass().getAnnotation(TelegramCommand.class).value(),
                        handler -> handler
                ));
    }

    public SendMessage handleCommands(Update update) {
        String messageText = update.getMessage().getText();
        String command = messageText.split(" ")[0];
        long chatId = update.getMessage().getChatId();

        var commandHandler = commands.get(command);
        if (commandHandler != null) {
            try {
                return commandHandler.apply(update);
            } catch (Exception e) {
                return new SendMessage(String.valueOf(chatId), e.getMessage());
            }
        } else {
            return new SendMessage(String.valueOf(chatId), Constants.UNKNOWN_COMMAND);
        }
    }

}
