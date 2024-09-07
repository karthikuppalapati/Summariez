package com.better.Summariez.controllers;

import com.better.Summariez.constants.ValidationConstants;
import com.better.Summariez.dtos.UserRegistrationDTO;
import com.better.Summariez.dtos.UserSignInDTO;
import com.better.Summariez.models.User;
import com.better.Summariez.respositories.UserRepository;
import com.better.Summariez.services.UserService;
import com.better.Summariez.utils.JwtUtils;
import com.better.Summariez.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired private UserRepository userRepo;
    @Autowired private UserService userService;
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
}
