package com.better.Summariez.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@Document("users")
public class User {
    @Id
    private String id;
    private String userName;
    private String password;
    @Indexed(unique = true)
    private String emailId;
    private Date dob;
    private LocalDateTime createdAt;
}
