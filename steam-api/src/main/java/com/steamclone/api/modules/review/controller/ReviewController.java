package com.steamclone.api.modules.review.controller;

import com.steamclone.api.modules.review.dto.CreateReviewRequest;
import com.steamclone.api.modules.review.dto.ReviewCreatedResponse;
import com.steamclone.api.modules.review.dto.ReviewResponse;
import com.steamclone.api.modules.review.dto.UpdateReviewRequest;
import com.steamclone.api.modules.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{gameId}")
    public ResponseEntity<ReviewCreatedResponse> createReview(
            @PathVariable UUID gameId,
            @RequestBody @Valid CreateReviewRequest request) {
        ReviewCreatedResponse response = reviewService.createReview(gameId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{gameId}")
    public Page<ReviewResponse> getReviews(
            @PathVariable UUID gameId,
            Pageable pageable
    ) {
        return reviewService.getGameReviews(gameId, pageable);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewCreatedResponse> updateReview(
            @PathVariable UUID reviewId,
            @RequestBody @Valid UpdateReviewRequest request
    ) {
        ReviewCreatedResponse response = reviewService.updateReview(reviewId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}