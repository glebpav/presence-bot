package com.xelari.presencebot.telegram;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidHandler {

    public static UUID longToUUID(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(0, 0);
        buffer.putLong(8, value);
        return new UUID(buffer.getLong(0), buffer.getLong(8));
    }

}
