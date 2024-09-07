package com.better.Summariez.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpUtils {
    public static ResponseEntity<String> executeGetRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(url, String.class);
    }
}
