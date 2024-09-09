package com.better.Summariez.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class JsonUtils {
    private static final Logger logger = LogManager.getLogger(JsonUtils.class);

    public static <T> T fromJson(String json, Class<T> object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, object);
        } catch (Exception e) {
            logger.error("Unable to map json to class" + e.getMessage());
        }
        return null;
    }

    public static <T> String toJson(T object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Unable to convert object to json. " + e.getMessage());
        }
        return null;
    }
}
