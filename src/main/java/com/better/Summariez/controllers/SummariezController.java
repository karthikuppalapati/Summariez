package com.better.Summariez.controllers;

import com.better.Summariez.dtos.SummaryDTO;
import com.better.Summariez.interfaces.IUserService;
import com.better.Summariez.models.User;
import com.better.Summariez.respositories.UserRepository;
import com.better.Summariez.services.SummaryService;
import com.better.Summariez.services.UserServiceImpl;
import com.better.Summariez.utils.JwtUtils;
import com.better.Summariez.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SummariezController {

    @Autowired private JwtUtils jwtUtils;
    @Autowired private UserRepository userRepo;
    @Autowired private UserServiceImpl userService;
    @Autowired private SummaryService summaryService;

    @PostMapping("/summary")
    public ResponseWrapper<String> postSummary(@RequestHeader(value = "Authorization") String authorization,
                                               @RequestParam(value = "volumeId") String volumeId,
                                               @RequestBody SummaryDTO summaryDTO) {
        String emailId = jwtUtils.getEmailFromToken(authorization);
        if(emailId.isEmpty() || emailId == null)
            return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(400, "Invalid token"));
        Optional<User> user = userRepo.findByEmailId(emailId);
        if(!user.isPresent())
            return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(404, "User not found"));
        summaryService.saveSummary(volumeId, user.get(), summaryDTO);
        return new ResponseWrapper<>(true, "Summary posted successfully", null);
    }
}
