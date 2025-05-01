package com.xelari.presencebot.telegram.command.handler.team;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.JsonHandler;
import com.xelari.presencebot.telegram.callback.Callback;
import com.xelari.presencebot.telegram.callback.CallbackDataCache;
import com.xelari.presencebot.telegram.callback.CallbackType;
import com.xelari.presencebot.telegram.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamCommand implements CommandHandler {

    @Override
    public SendMessage apply(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(Constants.SELECT_OPTION_MESSAGE);

        addKeyboard(message);

        return message;
    }

    private void addKeyboard(SendMessage sendMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        try {

            InlineKeyboardButton createTeamButton = new InlineKeyboardButton();
            createTeamButton.setText("Create Team");
            createTeamButton.setCallbackData(JsonHandler.toJson(
                    new Callback(CallbackType.CREATE_TEAM, new CallbackDataCache.DataKey())
            ));


            InlineKeyboardButton inviteMemberButton = new InlineKeyboardButton();
            inviteMemberButton.setText("Invite Member");
            inviteMemberButton.setCallbackData(JsonHandler.toJson(
                    new Callback(CallbackType.INVITE_MEMBER, new CallbackDataCache.DataKey())
            ));

            InlineKeyboardButton enterTeamButton = new InlineKeyboardButton();
            enterTeamButton.setText("Join Team");
            enterTeamButton.setCallbackData(JsonHandler.toJson(
                    new Callback(CallbackType.ENTER_TEAM, new CallbackDataCache.DataKey())
            ));

            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            rowList.add(List.of(createTeamButton, inviteMemberButton, enterTeamButton));

            inlineKeyboardMarkup.setKeyboard(rowList);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
