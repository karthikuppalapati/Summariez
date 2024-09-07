package com.better.Summariez.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class UserRegistrationDTO {
    private String userName;
    private String emailId;
    private String password;
    private Date dob;
}
