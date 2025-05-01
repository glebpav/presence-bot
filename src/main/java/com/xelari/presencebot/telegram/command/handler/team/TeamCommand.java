package com.xelari.presencebot.telegram.command.handler.team;

import com.xelari.presencebot.telegram.Constants;
import com.xelari.presencebot.telegram.JsonHandler;
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

        InlineKeyboardButton createTeamButton = new InlineKeyboardButton();
        createTeamButton.setText("Create Team");
        createTeamButton.setCallbackData(JsonHandler.toJson(CallbackType.CREATE_TEAM, null));

        InlineKeyboardButton inviteMemberButton = new InlineKeyboardButton();
        inviteMemberButton.setText("Invite Member");
        inviteMemberButton.setCallbackData(JsonHandler.toJson(CallbackType.INVITE_MEMBER, null));

        InlineKeyboardButton enterTeamButton = new InlineKeyboardButton();
        enterTeamButton.setText("Enter Team");
        enterTeamButton.setCallbackData(JsonHandler.toJson(CallbackType.ENTER_TEAM, null));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(List.of(createTeamButton, inviteMemberButton, enterTeamButton));

        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }

}
