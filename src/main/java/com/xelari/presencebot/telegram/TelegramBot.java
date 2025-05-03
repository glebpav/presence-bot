package com.xelari.presencebot.telegram;

import com.xelari.presencebot.telegram.operation.callback.CallbackDispatcher;
import com.xelari.presencebot.telegram.operation.command.CommandDispatcher;
import com.xelari.presencebot.telegram.config.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    public final BotConfig botConfig;
    public final CommandDispatcher commandDispatcher;
    public final CallbackDispatcher callbacksDispatcher;

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            if (update.getMessage().getText().startsWith("/")) {
                sendMessage(commandDispatcher.handleCommands(update));
            } else {
                sendMessage(new SendMessage(chatId, Constants.CANT_UNDERSTAND));
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
}