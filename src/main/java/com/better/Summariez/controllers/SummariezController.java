package com.better.Summariez.controllers;

import com.better.Summariez.constants.ValidationConstants;
import com.better.Summariez.dtos.PostSummaryRequestDTO;
import com.better.Summariez.models.User;
import com.better.Summariez.respositories.UserRepository;
import com.better.Summariez.services.SummaryService;
import com.better.Summariez.services.UserServiceImpl;
import com.better.Summariez.utils.JwtUtils;
import com.better.Summariez.utils.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
public class SummariezController {

    private static final Logger logger = LogManager.getLogger(SummariezController.class);

    @Autowired private JwtUtils jwtUtils;
    @Autowired private UserRepository userRepo;
    @Autowired private UserServiceImpl userService;
    @Autowired private SummaryService summaryService;

    @PostMapping("/summary")
    public ResponseWrapper<String> postSummary(@RequestHeader(value = "Authorization") String authorization,
                                               @RequestParam(value = "volumeId") String volumeId,
                                               @RequestBody PostSummaryRequestDTO summaryDTO) {
        try {
            String emailId = jwtUtils.getEmailFromToken(authorization);
            if(emailId == null || emailId.isEmpty())
                return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(400,
                        "Invalid token"));
            Optional<User> user = userRepo.findByEmailId(emailId);
            if(!user.isPresent())
                return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(404,
                        "User not found"));
            summaryService.saveSummary(volumeId, user.get(), summaryDTO);
            return new ResponseWrapper<>(true, "Summary posted successfully", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(500,
                ValidationConstants.INTERNAL_SERVER_ERROR));
    }

    @PostMapping("/like")
    public ResponseWrapper<String> likeSummary(@RequestHeader(value = "Authorization") String authorization,
                                               @RequestParam(value = "summaryId") String summaryId) {
        try {
            String emailId = jwtUtils.getEmailFromToken(authorization);
            if(emailId == null || emailId.isEmpty())
                return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(400,
                        "Invalid token"));
            Optional<User> user = userRepo.findByEmailId(emailId);
            if(user.isEmpty())
                return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(404,
                        "User not found"));
            summaryService.likeSummary(summaryId, user.get());
            return new ResponseWrapper<>(true, "Liked", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(500,
                ValidationConstants.INTERNAL_SERVER_ERROR));
    }
}
