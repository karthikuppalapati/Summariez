package com.better.Summariez.respositories;

import com.better.Summariez.models.Summary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SummaryRepository extends MongoRepository<Summary, String> {
    Integer countByUserId(String userId);
    List<Summary> findAllByVolumeId(String volumeId);
    Summary findByVolumeIdAndUserId(String volumeId, String userId);
}
