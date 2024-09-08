package com.better.Summariez.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("summariez")
@Builder
@Data
public class Summary {
    @Id
    private String id;
    private String content;
    private String userId;
    private String volumeId;
    private Integer likesCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
