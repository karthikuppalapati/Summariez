package com.better.Summariez.controllers;

import com.better.Summariez.utils.ResponseWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseWrapper<String> handleGlobalException(Exception e) {
        logger.error(e.getMessage());
        return new ResponseWrapper(false, null, new ResponseWrapper.ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}
