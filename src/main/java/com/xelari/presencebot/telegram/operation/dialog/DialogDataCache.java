package com.xelari.presencebot.telegram.operation.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DialogDataCache {

    private final Map<Long, Object> cache = new HashMap<>();
    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public void put(long chatId, Object data) {
        cache.put(chatId, data);
    }

    public <T> T getData(long chatId, Class<T> clazz) {
        var stage = cache.get(chatId);
        try {
            return mapper.convertValue(stage, clazz);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(
                    "Item's class is: " + stage.getClass()
            );
        }
    }

}
