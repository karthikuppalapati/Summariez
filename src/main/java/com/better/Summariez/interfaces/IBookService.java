package com.better.Summariez.interfaces;

import com.better.Summariez.dtos.BookSearchResponseDTO;

import java.util.List;

public interface IBookService {
    /**
     * Searches for a book by book name
     * @param bookName This is the book name searched by user
     * @return dto containing details of the matched books
     */
    public List<BookSearchResponseDTO> searchBookByName(String bookName);

    /**
     * Searches for a book by its id
     * @param id Here id is the id of the book in the 3rd party service database. For example Google Books API
     * @return
     */
    public BookSearchResponseDTO searchBookById(String id);
}
