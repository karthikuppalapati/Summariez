package com.better.Summariez.services;

import com.better.Summariez.dtos.ProfileDTO;
import com.better.Summariez.dtos.UserRegistrationDTO;
import com.better.Summariez.models.User;
import com.better.Summariez.respositories.LikeRepository;
import com.better.Summariez.respositories.SummaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserServiceImpl {

    @Autowired private SummaryRepository summaryRepo;
    @Autowired private LikeRepository likeRepo;

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

    public ProfileDTO fetchProfile(User user) {
        return constructProfileDTO(user);
    }

    private ProfileDTO constructProfileDTO(User user) {
        Integer summariezLiked = likeRepo.countByUserId(user.getId());
        Integer summariezPosted = summaryRepo.countByUserId(user.getId());
        return ProfileDTO.builder()
                .userName(user.getUserName())
                .emailId(user.getEmailId())
                .dob(user.getDob())
                .summariesLiked(summariezLiked)
                .summariesPosted(summariezPosted)
                .build();
    }
}
