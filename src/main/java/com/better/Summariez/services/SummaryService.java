package com.better.Summariez.services;

import com.better.Summariez.dtos.PostSummaryRequestDTO;
import com.better.Summariez.models.Like;
import com.better.Summariez.models.Summary;
import com.better.Summariez.models.User;
import com.better.Summariez.respositories.LikeRepository;
import com.better.Summariez.respositories.SummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class SummaryService {
    @Autowired private SummaryRepository summaryRepo;
    @Autowired private LikeRepository likeRepo;

    private Summary constructSummary(String volumeId, PostSummaryRequestDTO summaryDTO, User user) {
        return Summary.builder()
                .content(summaryDTO.getContent())
                .userId(user.getId())
                .volumeId(volumeId)
                .likesCount(0)
                .createdAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime())
                .build();
    }

    public Summary saveSummary(String volumeId, User user, PostSummaryRequestDTO summaryDTO) {
        Summary summary = constructSummary(volumeId, summaryDTO, user);
        return summary = summaryRepo.save(summary);
    }

    public Like likeSummary(String summaryId, User user) throws Exception {
        Optional<Summary> summary = summaryRepo.findById(summaryId);
        if(summary.isEmpty()) {
            throw new Exception("Summary does not exist");
        }
        summary.get().setLikesCount(summary.get().getLikesCount() + 1);
        summaryRepo.save(summary.get());
        Like like = constructLike(summaryId, user.getId(), summary.get().getVolumeId());
        return likeRepo.save(like);
    }

    private Like constructLike(String summaryId, String userId, String volumeId) {
        return Like.builder()
                .summaryId(summaryId)
                .userId(userId)
                .volumeId(volumeId)
                .build();
    }
}
