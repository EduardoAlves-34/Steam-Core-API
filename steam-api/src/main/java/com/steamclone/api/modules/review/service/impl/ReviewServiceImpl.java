package com.steamclone.api.modules.review.service.impl;

import com.steamclone.api.modules.game.entity.Game;
import com.steamclone.api.modules.game.repository.GameRepository;
import com.steamclone.api.modules.purchase.repository.PurchaseRepository;
import com.steamclone.api.modules.review.dto.CreateReviewRequest;
import com.steamclone.api.modules.review.dto.ReviewCreatedResponse;
import com.steamclone.api.modules.review.dto.ReviewResponse;
import com.steamclone.api.modules.review.dto.UpdateReviewRequest;
import com.steamclone.api.modules.review.entity.Review;
import com.steamclone.api.modules.review.repository.ReviewRepository;
import com.steamclone.api.modules.review.service.ReviewService;
import com.steamclone.api.modules.user.dto.UserResponse;
import com.steamclone.api.modules.user.entity.User;
import com.steamclone.api.modules.user.enums.Role;
import com.steamclone.api.modules.user.repository.UserRepository;
import com.steamclone.api.modules.user.service.UserService;
import com.steamclone.api.shared.exception.BusinessException;
import com.steamclone.api.shared.exception.ForbiddenException;
import com.steamclone.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserService userService;

    @CacheEvict(value = "game", allEntries = true)
    @Override
    public ReviewCreatedResponse createReview(UUID gameId, CreateReviewRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Game not found"));

        boolean purchased = purchaseRepository
                .findByUser(user, Pageable.unpaged())
                .stream()
                .anyMatch(p -> p.getGame().getId().equals(gameId));

        if (!purchased) {
            throw new BusinessException("You can only review games you have purchased");
        }

        if (reviewRepository.findByUserAndGame(user, game).isPresent()) {
            throw new BusinessException("You have already reviewed this game");
        }

        Review review = Review.builder()
                .user(user)
                .game(game)
                .rating(request.rating())
                .comment(request.comment())
                .build();

        Review savedReview = reviewRepository.save(review);

        return new ReviewCreatedResponse(
                savedReview.getGame().getName(),
                savedReview.getUser().getUsername(),
                savedReview.getRating(),
                savedReview.getComment(),
                savedReview.getCreatedAt()
        );
    }

    @Override
    public Page<ReviewResponse> getGameReviews(UUID gameId, Pageable pageable) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Game not found"));

        return reviewRepository.findByGame(game, pageable)
                .map(r -> new ReviewResponse(
                        r.getId(),
                        r.getUser().getUsername(),
                        r.getRating(),
                        r.getComment(),
                        r.getCreatedAt()
                ));
    }

    @Override
    public ReviewCreatedResponse updateReview(UUID reviewId, UpdateReviewRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review not found"));

        if (!review.getUser().getEmail().equals(user.getEmail())) {
            throw new BusinessException("You can only edit your own reviews");
        }

        review.setRating(request.rating());
        review.setComment(request.comment());

        Review savedReview = reviewRepository.save(review);

        return new ReviewCreatedResponse(
                savedReview.getGame().getName(),
                savedReview.getUser().getUsername(),
                savedReview.getRating(),
                savedReview.getComment(),
                savedReview.getCreatedAt()
        );
    }

    public void deleteReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        UserResponse currentUser = userService.getCurrentUser();

        boolean isOwner = review.getUser().getId().equals(currentUser.id());
        boolean isAdmin = currentUser.role().equals(Role.ADMIN);

        if (!isOwner && !isAdmin) {
            throw new ForbiddenException("You do not have permission to delete this review");
        }

        reviewRepository.delete(review);
    }

}