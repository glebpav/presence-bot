package com.xelari.presencebot.telegram;

import com.xelari.presencebot.application.dto.meeting.MeetingResponse;
import com.xelari.presencebot.domain.entity.token.InvitationToken;
import com.xelari.presencebot.domain.entity.meeting.Meeting;
import com.xelari.presencebot.domain.entity.team.Team;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Constants {

    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";

    public static final String START_MESSAGE = "Hello, this is ...";
    public static final String HEALTH_MESSAGE = "Bot is alive";

    public static final String UNKNOWN_COMMAND = "Sorry, I don't know this command";
    public static final String HELP_COMMAND_MESSAGE = "This bot is ";
    public static final String CANT_UNDERSTAND = "Sorry, I can't understand your input";
    public static final String ERROR = "Internal error";
    public static final String JSON_ERROR = "JSON error";

    public static final String SPACE_BETWEEN = "\n\n";
    public static final String INPUT_CANT_BE_BLANK_MESSAGE = "Your input can't be blank";
    public static final String SELECT_OPTION_MESSAGE = "Select option:";
    public static final String GREETING_MESSAGE = "Welcome to Xelari-presence-bot!";
    public static final String SUCCESSFULLY_ADDED_MESSAGE = "Successfully register you!";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "It seamed that you are already registered";
    public static final String USER_NOT_FOUND_MESSAGE = "It seamed that you are not registered!";
    public static final String NO_BELONG_TEAM_MESSAGE = "It seamed that you doesn't belong to any team!";
    public static final String USER_HAVE_NOT_MANAGING_TEAMS_MESSAGE = "It seems that you haven't managed teams!";
    public static final String SELECT_TEAM_MESSAGE = "Select team:";
    public static final String SELECT_ROLE_MESSAGE = "Select role:";
    public static final String ENTER_TEAM_NAME_MESSAGE = "Please, enter team name";
    public static final String JOIN_TEAM_MESSAGE = "Please, enter invitation token";
    public static final String TEAM_WAS_CREATED_MESSAGE = "Successfully create team!";
    public static final String TEAM_ALREADY_EXISTS_MESSAGE = "Team with this name already exists!";
    public static final String INVITATION_TOKEN_CREATED_MESSAGE = "Your invitation token was created successfully!";
    public static final String TOKEN_NOT_EXISTS_MESSAGE = "Your invitation token does not exist!";
    public static final String ALREADY_IN_TEAM_MESSAGE = "You are already in this team!";
    public static final String TOKEN_EXPIRED_MESSAGE = "Your invitation token has expired!";

    public static String SUCCESSFUL_CREATE_INITIATION_TOKEN_MESSAGE(InvitationToken token) {
        return "<b>🎟️ Invitation Token Created 🎟️</b>\n\n" +
                Constants.INVITATION_TOKEN_CREATED_MESSAGE + "\n\n" +
                "⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺\n" +
                "<b>🔑 Token:</b> <code>" + token.getToken() + "</code>\n" +
                "<b>👥 Role:</b> " + token.getAssignedRole() + "\n" +
                "<b>⏳ Expires:</b> " +
                token.getExpiresAt()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm")) + "\n" +
                "⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺⸺\n\n" +
                "<i>Share this token securely!</i>";
    }

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

    public static String ENTER_MEETING_NAME_MESSAGE = "Please, enter meeting name";
    public static String INPUT_MEETING_TIME_MESSAGE = "Please, enter meeting time (in format <code>yyyy.MM.dd HH:mm</code>)";
    public static String INPUT_NEW_MEETING_TIME_MESSAGE = "Please, enter new meeting time (in format <code>yyyy.MM.dd HH:mm</code>)";
    public static String ENTER_MEETING_DURATION_MESSAGE = "Please, enter meeting duration (in minutes)";
    public static String ENTER_MEETING_DESCRIPTION_MESSAGE = "Please, enter meeting description";
    public static String ENTER_MEETING_REPEAT_COUNT_MESSAGE = "Please, enter repeat count";
    public static String SELECT_MEETING_REPETITIONS_MESSAGE = "Please, enter count of repetitions:";
    public static String MEETING_CREATED_SUCCESSFULLY_MESSAGE = "Meeting(s) was(were) created successfully!";
    public static String INCORRECT_TIME_FORMAT_MESSAGE = "I can't understand your input";

    public static String SELECT_MEETING_SHOW_TYPE_MESSAGE = "Please, select to show meeting for appropriate team or all your meetings";
    public static String SELECT_TEAM_FOR_MEETING_MESSAGE = "Please, select team witch meeting you want to edit";
    public static String NO_MEETING_WAS_FOUND_MESSAGE = "No meeting was found!";
    public static String NO_MANAGING_MEETING_WAS_FOUND_MESSAGE = "No managing meeting was found!";
    public static String NO_MATCHING_MEETING_IN_LIST_MESSAGE = "No matching meeting in list!";
    public static String MEETING_DELETED_SUCCESSFULLY_MESSAGE = "Meeting was deleted successfully!";
    public static String MEETING_TIME_WAS_CHANGED_SUCCESSFULLY_MESSAGE = "Meeting time was changed successfully!";

    public static String FOUND_MEETING_MESSAGE(List<MeetingResponse> meetings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < meetings.size(); i++) {
            stringBuilder
                    .append("%d. ".formatted(i + 1))
                    .append(meetings.get(i).toString())
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    private static String escapeHtml(String input) {
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

}
