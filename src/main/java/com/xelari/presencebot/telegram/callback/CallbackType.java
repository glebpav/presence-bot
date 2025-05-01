package com.xelari.presencebot.telegram.callback;

public enum CallbackType {
    // `/team` command callbacks
    CREATE_TEAM,

    INVITE_MEMBER,
    SELECT_ROLE_FOR_INVITATION,
    GENERATE_TOKEN_FOR_INVITATION,
    ENTER_TEAM
}
