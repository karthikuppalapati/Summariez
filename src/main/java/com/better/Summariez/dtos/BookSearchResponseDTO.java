package com.better.Summariez.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookSearchResponseDTO {
    private String id;
    private String title;
    private List<String> authors;
    private Integer pageCount;
    private String description;
    private String publisher;
    private String publishedDate;
}
