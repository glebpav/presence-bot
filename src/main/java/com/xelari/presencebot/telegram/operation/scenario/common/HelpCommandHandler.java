package com.xelari.presencebot.telegram.operation.scenario.common;

import com.xelari.presencebot.telegram.operation.command.CommandHandler;
import com.xelari.presencebot.telegram.operation.command.TelegramCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@TelegramCommand("/help")
public class HelpCommandHandler implements CommandHandler {
    @Override
    public SendMessage apply(Update update) {
        var message = new SendMessage(
                String.valueOf(getChatId(update)),
                """
                        <b>Welcome to Presence Bot!</b>
                        <i>Xelari's Team & Meeting Management System</i>
                        
                        📌 <b>MAIN COMMANDS</b>
                        /start - Register in the system (all users)
                        /team - Teams management (all users)
                        /meeting - Meetings management (all users)
                        
                        🔐 <b>ACCESS LEVELS</b>
                        All commands verify your access rights:
                        • Regular users: Basic interaction
                        • Managers: Full team management
                        
                        ❓ <b>SUPPORT</b>
                        Report issues through:
                        • <a href="https://github.com/glebpav/presence-bot">GitHub Issues</a>
                        • Xelari internal chat
                        
                        🚀 Begin with <code>/start</code> to register in the system!
                        """
        );

        message.enableHtml(true);
        message.disableWebPagePreview();

        return message;
    }
}