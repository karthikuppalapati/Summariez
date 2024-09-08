package com.better.Summariez.services;

import com.better.Summariez.dtos.SummaryDTO;
import com.better.Summariez.dtos.UserRegistrationDTO;
import com.better.Summariez.models.Summary;
import com.better.Summariez.models.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserServiceImpl {
    // Constructs User model object from the registrationDTO
    public User constructUser(UserRegistrationDTO registrationDTO) {
        User user = User.builder()
                .userName(registrationDTO.getUserName())
                .emailId(registrationDTO.getEmailId())
                .password(registrationDTO.getPassword())
                .dob(registrationDTO.getDob())
                .createdAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime())
                .build();
        return user;
    }
}
