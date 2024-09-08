package com.better.Summariez.respositories;

import com.better.Summariez.models.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LikeRepository extends MongoRepository<Like, String> {
}
