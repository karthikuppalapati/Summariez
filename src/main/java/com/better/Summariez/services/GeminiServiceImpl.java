package com.better.Summariez.services;

import com.better.Summariez.dtos.GeminiRequestDTO;
import com.better.Summariez.dtos.GeminiResponseDTO;
import com.better.Summariez.interfaces.IAIService;
import com.better.Summariez.models.Book;
import com.better.Summariez.models.Summary;
import com.better.Summariez.respositories.BookRepository;
import com.better.Summariez.respositories.SummaryRepository;
import com.better.Summariez.utils.HttpUtils;
import com.better.Summariez.utils.JsonUtils;
import com.better.Summariez.utils.ResponseWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeminiServiceImpl implements IAIService {
    private static final Logger logger = LogManager.getLogger(GeminiServiceImpl.class);

    @Value("${google.gemini.api.textOutput}")
    private String geminiUrl;

    @Value("${google.gemini.api.key}")
    private String apiKey;

    private static final String AI_USER = "AI";
//    @Autowired private JsonUtils jsonUtils;
    @Autowired private BookRepository bookRepo;
    @Autowired private SummaryRepository summaryRepo;

    @Override
    public String getAISummary(String volumeId) {
        try {
            Optional<Book> book = bookRepo.findById(volumeId);
            String bookName = book.get().getName();
            Summary summary = summaryRepo.findByVolumeIdAndUserId(volumeId, AI_USER);
            if(summary == null) {
                geminiUrl = geminiUrl + apiKey;
                String requestPayload = JsonUtils.toJson(constructRequestDTO(bookName));
                ResponseEntity<String> response = HttpUtils.executePostRequest(geminiUrl, requestPayload, null);
                String responsePayload = response.getBody();
                GeminiResponseDTO responseDTO = JsonUtils.fromJson(responsePayload, GeminiResponseDTO.class);
                if(responseDTO != null) {
                    String aiSummary = responseDTO.getCandidates().get(0).getContent().getParts().get(0).getText();
                    summaryRepo.save(constructAISummary(aiSummary, volumeId));
                    return aiSummary;
                }
            }
            return summary.getContent();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private GeminiRequestDTO constructRequestDTO(String bookName) {
        StringBuilder prompt = new StringBuilder("Write a short summary of the book - ");
        prompt.append(bookName);

        List<GeminiRequestDTO.Content> contents = new ArrayList<>();
        List<GeminiRequestDTO.Part> parts = new ArrayList<>();
        GeminiRequestDTO.Content content = new GeminiRequestDTO.Content();
        GeminiRequestDTO.Part part = new GeminiRequestDTO.Part();
        part.setText(prompt.toString());
        parts.add(part);
        content.setParts(parts);
        contents.add(content);

        return GeminiRequestDTO.builder()
                .contents(contents)
                .build();
    }

    private Summary constructAISummary(String summary, String volumeId) {
        return Summary.builder()
                .content(summary)
                .userId(AI_USER)
                .volumeId(volumeId)
                .likesCount(0)
                .createdAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime())
                .build();
    }
}
