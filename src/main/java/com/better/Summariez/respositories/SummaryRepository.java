package com.better.Summariez.respositories;

import com.better.Summariez.models.Summary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SummaryRepository extends MongoRepository<Summary, String> {
}
