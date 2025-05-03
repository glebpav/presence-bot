package com.xelari.presencebot.telegram.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xelari.presencebot.telegram.JsonHandler;
import com.xelari.presencebot.telegram.operation.callback.Callback;
import com.xelari.presencebot.telegram.operation.callback.CallbackDataCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ButtonBuilder {

    private final CallbackDataCache callbackDataCache;

    public void addHorizontalKeyboard(SendMessage message, List<ButtonDescription> buttonsDescriptions) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (ButtonDescription buttonDescription : buttonsDescriptions) {
            buttons.add(createInlineKeyboardButton(buttonDescription));
        }

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(buttons);

        inlineKeyboardMarkup.setKeyboard(rowList);
        message.setReplyMarkup(inlineKeyboardMarkup);
    }

    public void addVerticalKeyboard(SendMessage message, List<ButtonDescription> buttonsDescriptions) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (ButtonDescription buttonDescription : buttonsDescriptions) {
            rowList.add(List.of(createInlineKeyboardButton(buttonDescription)));
        }

        inlineKeyboardMarkup.setKeyboard(rowList);
        message.setReplyMarkup(inlineKeyboardMarkup);
    }

    private InlineKeyboardButton createInlineKeyboardButton(ButtonDescription buttonDescription) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonDescription.text());
        try {
            button.setCallbackData(
                    JsonHandler.toJson(
                            new Callback(
                                    buttonDescription.callbackType(),
                                    callbackDataCache.putData(buttonDescription.data())
                            )
                    )
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return button;
    }

}
