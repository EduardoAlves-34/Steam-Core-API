package com.steamclone.api.modules.review.service;

import com.steamclone.api.modules.review.dto.CreateReviewRequest;
import com.steamclone.api.modules.review.dto.ReviewCreatedResponse;
import com.steamclone.api.modules.review.dto.ReviewResponse;
import com.steamclone.api.modules.review.dto.UpdateReviewRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReviewService {
    ReviewCreatedResponse createReview(UUID gameId, CreateReviewRequest request);

    Page<ReviewResponse> getGameReviews(UUID gameId, Pageable pageable);

    ReviewCreatedResponse updateReview(UUID reviewId, UpdateReviewRequest request);

    void deleteReview(UUID reviewId);
}
