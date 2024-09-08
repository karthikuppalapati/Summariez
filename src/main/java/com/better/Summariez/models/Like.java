package com.better.Summariez.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("likes")
@Builder
public class Like {
    @Id
    private String id;
    private String summaryId;
    private String userId;
    private String volumeId;
}
