package com.better.Summariez.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpUtils {
    public static ResponseEntity<String> executeGetRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(url, String.class);
    }

    public static ResponseEntity<String> executePostRequest(String url, String json, HttpHeaders headers) {
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }
}
