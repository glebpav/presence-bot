package com.xelari.presencebot.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xelari.presencebot.telegram.callback.CallbackType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JsonHandler {
    private final static ObjectMapper mapper = new ObjectMapper();

    @Deprecated
    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return Constants.ERROR;
        }
    }

    public static String toJson(CallbackType callbackType, Object data) {
        try {
            data = (data == null) ? "" : data;
            var dataList = List.of(callbackType, data);
            return mapper.writeValueAsString(dataList);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return Constants.ERROR;
        }
    }

    public static <T> T toObject(Object object, Class<T> clazz) {
        try {
            return mapper.convertValue(object, clazz);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public static List<String> toList(String json) {
        try {
            return mapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return List.of();
        }
    }

}
