package com.better.Summariez.respositories;

import com.better.Summariez.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
