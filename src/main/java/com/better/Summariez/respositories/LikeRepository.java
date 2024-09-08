package com.better.Summariez.respositories;

import com.better.Summariez.models.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
    Integer countByUserId(String userId);
}
