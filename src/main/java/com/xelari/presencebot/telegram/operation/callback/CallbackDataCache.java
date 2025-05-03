package com.xelari.presencebot.telegram.operation.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xelari.presencebot.telegram.JsonHandler;
import jakarta.inject.Singleton;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
@Component
public class CallbackDataCache {

    @Getter
    @EqualsAndHashCode
    public static class DataKey {
        final private int key = UUID.randomUUID().hashCode();
    }

    private final Map<DataKey, Object> cache = new HashMap<>();
    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    private  <T> T getData(DataKey key, Class<T> clazz) {
        if (!cache.containsKey(key)) {
            throw new IllegalArgumentException("Key not found: " + key);
        }
        Object data = cache.get(key);
        try {
            return mapper.convertValue(data, clazz);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Item's class is: " + data.getClass());
        }
    }

    public DataKey putData(Object data) {
        DataKey key = new DataKey();
        cache.put(key, data);
        return key;
    }

    public <T> T getData(Callback callback, Class<T> clazz) {
        DataKey dataKey = JsonHandler.toObject(callback.getDataKey(), DataKey.class);
        return getData(dataKey, clazz);
    }

}
