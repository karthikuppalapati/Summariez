package com.better.Summariez.controllers;

import com.better.Summariez.dtos.BookSearchResponseDTO;
import com.better.Summariez.interfaces.IBookService;
import com.better.Summariez.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired private IBookService bookService;

    @GetMapping("/search")
    public ResponseWrapper<List<BookSearchResponseDTO>> fetchBooksByName(@RequestParam(value = "bookName", required = true) String bookName) {
        List<BookSearchResponseDTO> searchResponseDTOS = bookService.searchBookByName(bookName);
        return new ResponseWrapper<>(true, searchResponseDTOS, null);
    }
}
