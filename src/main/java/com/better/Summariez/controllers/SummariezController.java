package com.better.Summariez.controllers;

import com.better.Summariez.constants.ValidationConstants;
import com.better.Summariez.dtos.PostSummaryRequestDTO;
import com.better.Summariez.interfaces.IAIService;
import com.better.Summariez.models.Book;
import com.better.Summariez.models.Summary;
import com.better.Summariez.models.User;
import com.better.Summariez.respositories.BookRepository;
import com.better.Summariez.respositories.SummaryRepository;
import com.better.Summariez.respositories.UserRepository;
import com.better.Summariez.services.SummaryService;
import com.better.Summariez.services.UserServiceImpl;
import com.better.Summariez.utils.JwtUtils;
import com.better.Summariez.utils.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class SummariezController {

    private static final Logger logger = LogManager.getLogger(SummariezController.class);

    @Autowired private JwtUtils jwtUtils;
    @Autowired private UserRepository userRepo;
    @Autowired private UserServiceImpl userService;
    @Autowired private SummaryService summaryService;
    @Autowired private SummaryRepository summaryRepo;
    @Autowired private BookRepository bookRepo;
    @Autowired private IAIService aiService;

    @PostMapping("/summary")
    public ResponseWrapper<String> postSummary(@RequestHeader(value = "Authorization") String authorization,
                                               @RequestParam(value = "volumeId") String volumeId,
                                               @RequestBody PostSummaryRequestDTO summaryDTO) {
        String emailId = jwtUtils.getEmailFromToken(authorization);
        if(emailId == null || emailId.isEmpty())
            return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(), "Invalid token"));
        Optional<User> user = userRepo.findByEmailId(emailId);
        if(!user.isPresent())
            return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(
                    HttpStatus.NOT_FOUND.value(), "User not found"));
        summaryService.saveSummary(volumeId, user.get(), summaryDTO);
        return new ResponseWrapper<>(true, "Summary posted successfully", null);
    }

    @PostMapping("/like")
    public ResponseWrapper<String> likeSummary(@RequestHeader(value = "Authorization") String authorization,
                                               @RequestParam(value = "summaryId") String summaryId) throws Exception {
        String emailId = jwtUtils.getEmailFromToken(authorization);
        if(emailId == null || emailId.isEmpty())
            return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(), "Invalid token"));
        Optional<User> user = userRepo.findByEmailId(emailId);
        if(user.isEmpty())
            return new ResponseWrapper<>(false, null, new ResponseWrapper.ErrorResponse(
                    HttpStatus.NOT_FOUND.value(), "User not found"));
        summaryService.likeSummary(summaryId, user.get());
        return new ResponseWrapper<>(true, "Liked", null);
    }

    @GetMapping("/summariez")
    public ResponseWrapper<List<Summary>> fetchBookSummariez(@RequestParam(value = "volumeId") String volumeId,
                                                             @RequestParam(value = "bookName") String bookName) {
        Optional<Book> book = bookRepo.findById(volumeId);
        if(book.isEmpty()) {
            Book newBook = Book.builder()
                    .id(volumeId)
                    .name(bookName)
                    .createdAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime())
                    .build();
            bookRepo.save(newBook);
        }
        aiService.getAISummary(volumeId);
        return new ResponseWrapper<>(true, summaryRepo.findAllByVolumeId(volumeId), null);
    }
}
