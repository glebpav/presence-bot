package com.xelari.presencebot.telegram;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xelari.presencebot.telegram.callback.Callback;
import com.xelari.presencebot.telegram.callback.CallbackDataCache;
import com.xelari.presencebot.telegram.callback.CallbackType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonHandler {
    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Callback.class, new CallbackSerializer());
        module.addDeserializer(Callback.class, new CallbackDeserializer());
        mapper.registerModule(module);
        mapper.registerModule(new JavaTimeModule());
    }

    public static String toJson(Callback callback) throws JsonProcessingException {
        return mapper.writeValueAsString(callback);
    }

    public static Callback fromJson(String json) throws JsonProcessingException {
        return mapper.readValue(json, Callback.class);
    }

    public static <T> T toObject(Object object, Class<T> clazz) {
        try {
            return mapper.convertValue(object, clazz);
        } catch (ClassCastException e) {
            return null;
        }
    }

    private static class CallbackSerializer extends JsonSerializer<Callback> {
        @Override
        public void serialize(Callback callback, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartArray();
            gen.writeString(callback.getCallbackType().name());
            gen.writeObject(callback.getDataKey());
            gen.writeEndArray();
        }
    }

    private static class CallbackDeserializer extends JsonDeserializer<Callback> {
        @Override
        public Callback deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            CallbackType type = CallbackType.valueOf(node.get(0).asText());
            CallbackDataCache.DataKey dataKey = mapper.treeToValue(node.get(1), CallbackDataCache.DataKey.class);
            return new Callback(type, dataKey);
        }
    }

}
