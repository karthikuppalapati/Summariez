package com.better.Summariez.services;

import com.better.Summariez.dtos.BookSearchResponseDTO;
import com.better.Summariez.dtos.GoogleSearchResponseDTO;
import com.better.Summariez.interfaces.IBookService;
import com.better.Summariez.utils.HttpUtils;
import com.better.Summariez.utils.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements IBookService {

    private static final Logger logger = LogManager.getLogger(BookServiceImpl.class);

    @Value("${google.books.api.searchBook}")
    String searchBookAPI;
    /**
     * Searches for a book by book name
     * @param bookName This is the book name searched by user
     * @return dto containing details of the matched books
     */
    @Override
    public List<BookSearchResponseDTO> searchBookByName(String bookName) {
        List<BookSearchResponseDTO> result = new ArrayList<>();
        try {
            String searchURL = searchBookAPI.concat(bookName);
            ResponseEntity<String> response = HttpUtils.executeGetRequest(searchURL);
            String responsePayload = response.getBody();
            GoogleSearchResponseDTO searchRespDTO = JsonUtils.fromJson(responsePayload, GoogleSearchResponseDTO.class);
            if(searchRespDTO != null) {
                List<GoogleSearchResponseDTO.Volume> volumes = searchRespDTO.getItems();
                for(GoogleSearchResponseDTO.Volume volume : volumes) {
                    BookSearchResponseDTO bookDTO = new BookSearchResponseDTO();
                    bookDTO.setId(volume.getId());
                    bookDTO.setDescription(volume.getVolumeInfo().getDescription());
                    bookDTO.setAuthors(volume.getVolumeInfo().getAuthors());
                    bookDTO.setPublisher(volume.getVolumeInfo().getPublisher());
                    bookDTO.setTitle(volume.getVolumeInfo().getTitle());
                    bookDTO.setPageCount(volume.getVolumeInfo().getPageCount());
                    bookDTO.setPublishedDate(volume.getVolumeInfo().getPublishedDate());

                    result.add(bookDTO);
                }
            }
        } catch (Exception e) {
            logger.error("Exception - " + e.getMessage());
        }

        return result;
    }

    /**
     * Searches for a book by its id
     * @param id Here id is the id of the book in the 3rd party service database. For example Google Books API
     * @return
     */
    @Override
    public List<BookSearchResponseDTO> searchBookById(String id) {
        return null;
    }
}
