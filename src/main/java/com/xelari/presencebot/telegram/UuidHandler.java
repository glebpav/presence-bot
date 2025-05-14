package com.xelari.presencebot.telegram;

import java.util.UUID;

public class UuidHandler {

    public static UUID longToUUID(long value) {
        return new UUID(value, 0L);
    }

    public static long uuidToLong(UUID uuid) {
        return uuid.getMostSignificantBits();
    }

}
