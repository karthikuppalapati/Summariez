package com.better.Summariez.controllers;

import com.better.Summariez.constants.ValidationConstants;
import com.better.Summariez.dtos.ProfileDTO;
import com.better.Summariez.dtos.UserRegistrationDTO;
import com.better.Summariez.dtos.UserSignInDTO;
import com.better.Summariez.models.User;
import com.better.Summariez.respositories.UserRepository;
import com.better.Summariez.services.UserServiceImpl;
import com.better.Summariez.utils.JwtUtils;
import com.better.Summariez.utils.ResponseWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired private UserRepository userRepo;
    @Autowired private UserServiceImpl userService;
    @Autowired private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseWrapper<String> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        Optional<User> user = userRepo.findByEmailId(registrationDTO.getEmailId());
        if(user.isPresent()) {
            return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(), ValidationConstants.EMAIL_IN_USE));
        }
        User newUser = userRepo.save(userService.constructUser(registrationDTO));
        return new ResponseWrapper<>(true, jwtUtils.generateToken(newUser.getEmailId()), null);
    }

    @PostMapping("/signIn")
    public ResponseWrapper<String> signInUser(@RequestBody UserSignInDTO signInDTO) {
        Optional<User> user = userRepo.findByEmailId(signInDTO.getEmailId());
        if(user.isPresent()) {
            if(user.get().getPassword().equals(signInDTO.getPassword())) {
                return new ResponseWrapper<>(true, jwtUtils.generateToken(user.get().getEmailId()), null);
            }
        }
        return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(
                HttpStatus.NOT_FOUND.value(), ValidationConstants.USER_NOT_FOUND));
    }

    @GetMapping("/profile")
    public ResponseWrapper<ProfileDTO> fetchUserProfile(@RequestHeader(value = "Authorization") String authorization) {
        try {
            String emailId = jwtUtils.getEmailFromToken(authorization);
            if(emailId == null || emailId.isEmpty())
                return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(400,
                        "Invalid token"));
            Optional<User> user = userRepo.findByEmailId(emailId);
            return user.map(value -> new ResponseWrapper<>(true, userService.fetchProfile(value), null))
                    .orElseGet(() -> new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse
                            (404, "User not found")));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(500,
                ValidationConstants.INTERNAL_SERVER_ERROR));
    }
}
