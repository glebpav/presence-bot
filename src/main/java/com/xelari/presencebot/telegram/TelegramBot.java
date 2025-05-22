package com.xelari.presencebot.telegram;

import com.xelari.presencebot.telegram.config.BotConfig;
import com.xelari.presencebot.telegram.operation.callback.CallbackDispatcher;
import com.xelari.presencebot.telegram.operation.command.CommandDispatcher;
import com.xelari.presencebot.telegram.operation.dialog.DialogDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    public final BotConfig botConfig;
    public final CommandDispatcher commandDispatcher;
    public final CallbackDispatcher callbacksDispatcher;
    public final DialogDispatcher dialogDispatcher;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText() && update.getMessage().getText().startsWith("/")) {
                sendMessage(commandDispatcher.handleCommands(update));
            } else {
                sendMessage(dialogDispatcher.handleDialogs(update));
            }
        } else if (update.hasCallbackQuery()) {
            sendMessage(callbacksDispatcher.handleCallbacks(update));
        }
    }

    private void sendMessage(SendMessage sendMessage) throws TelegramApiException {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            // TODO: remove after debug
            throw e;
        }
    }

    public InputStream downloadTelegramFile(String fileId) throws TelegramApiException {
        GetFile getFile = new GetFile(fileId);
        File file = execute(getFile);
        return downloadFileAsStream(file);
    }
}