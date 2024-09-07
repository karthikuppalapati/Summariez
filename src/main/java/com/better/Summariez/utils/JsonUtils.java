package com.better.Summariez.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}
