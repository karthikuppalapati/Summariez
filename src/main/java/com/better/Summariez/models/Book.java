package com.better.Summariez.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Document("books")
@Builder
public class Book {
    @Id
    private String id;
    private String name;
    private LocalDateTime createdAt;
}
