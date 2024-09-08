package com.better.Summariez.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private String userName;
    private String emailId;
    private Date dob;
    private Integer summariesPosted;
    private Integer summariesLiked;
}
