package com.better.Summariez.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatGPTService {

    @Value("${chatgpt.api}")
    private String chatGPTAPIURL;

    @Value("${chatgpt.apiKey}")
    private String chatGPTAPIKey;

    public String getBookSummary(String bookTitle) {
        RestTemplate restTemplate = new RestTemplate();

        // Create the prompt for ChatGPT
        String prompt = "Give a detailed summary of the book titled '" + bookTitle + "'.";

        HttpHeaders headers = getHeaders();
        Map<String, Object> requestBody = getRequestBody(prompt);

        // Create the request entity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send the POST request to the OpenAI API
        ResponseEntity<Map> response = restTemplate.exchange(
                chatGPTAPIURL,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        // Parse the response to extract the summary
        Map<String, Object> choices = (Map<String, Object>) ((Map<String, Object>) response.getBody().get("choices")).get(0);
        return (String) choices.get("message");
    }

    private HttpHeaders getHeaders() {
        // Prepare request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + chatGPTAPIKey);
        headers.set("Content-Type", "application/json");
        return headers;
    }

    private Map<String, Object> getRequestBody(String prompt) {
        // Request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo"); // You can change the model based on your needs
        requestBody.put("messages", new Object[] {
                new HashMap<String, String>() {{
                    put("role", "user");
                    put("content", prompt);
                }}
        });
        return requestBody;
    }
}
