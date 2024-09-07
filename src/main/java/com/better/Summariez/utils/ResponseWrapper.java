package com.better.Summariez.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

// Use this class for all the Rest API responses to keep the responses standard across the project

@Data
@AllArgsConstructor
public class ResponseWrapper<T> {
    private boolean success;
    private T payload;
    private ErrorResponse error;

    @Data
    @AllArgsConstructor
    public static class ErrorResponse {
        private Integer code;
        private String message;
    }
}
