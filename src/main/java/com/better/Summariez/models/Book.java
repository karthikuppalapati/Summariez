package com.better.Summariez.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@Document
public class Book {
    @Id
    private String id;
    private String name;
    private Timestamp createdAt;
}
