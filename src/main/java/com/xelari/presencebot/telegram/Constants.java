package com.xelari.presencebot.telegram;

import com.xelari.presencebot.domain.entity.Team;

public class Constants {

    public static final String START_MESSAGE = "Hello, this is ...";
    public static final String HEALTH_MESSAGE = "Bot is alive";

    public static final String UNKNOWN_COMMAND = "Sorry, I don't know this command";
    public static final String CANT_UNDERSTAND = "Sorry, I can't understand your input";
    public static final String ERROR = "Internal error";
    public static final String JSON_ERROR = "JSON error";

    public static final String SPACE_BETWEEN = "\n\n";
    public static final String SELECT_OPTION_MESSAGE = "Select option:";
    public static final String GREETING_MESSAGE = "Welcome to Xelari-presence-bot!";
    public static final String SUCCESSFULLY_ADDED_MESSAGE = "Successfully register you!";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "It seamed that you are already registered";
    public static final String USER_NOT_FOUND_MESSAGE = "It seamed that you are not registered!";
    public static final String USER_HAVE_NOT_MANAGING_TEAMS_MESSAGE = "It seems that you haven't managed teams!";
    public static final String SELECT_TEAM_MESSAGE = "Select team:";
    public static final String SELECT_ROLE_MESSAGE = "Select role:";
    public static final String ENTER_TEAM_NAME_MESSAGE = "Please enter command\n<code>/create_team [name]</code>\nto create team";
    public static final String JOIN_TEAM_MESSAGE = "Please enter command\n<code>/join_team [token]</code>\nto join team";
    public static final String TEAM_WAS_CREATED_MESSAGE = "Successfully create team!";
    public static final String TEAM_ALREADY_EXISTS_MESSAGE = "Team with this name already exists!";
    public static final String INVITATION_TOKEN_CREATED_MESSAGE = "Your invitation token was created successfully!";
    public static final String TOKEN_NOT_EXISTS_MESSAGE = "Your invitation token does not exist!";
    public static final String ALREADY_IN_TEAM_MESSAGE = "You are already in this team!";
    public static final String TOKEN_EXPIRED_MESSAGE = "Your invitation token has expired!";

    public static String SUCCESSFUL_JOINED_TEAM_MESSAGE(Team team) {
        return String.format("""
        <b>🎉 Добро пожаловать в команду!</b>
        
        <b>🏷 Название:</b> <code>%s</code>
        
        <b>👥 Участники:</b> %d
        <b>📅 Активные встречи:</b> %d
        """,
                escapeHtml(team.getName()),
                team.getMembers().size(),
                team.getMeetings().size()
        );
    }

    private static String escapeHtml(String input) {
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

}
