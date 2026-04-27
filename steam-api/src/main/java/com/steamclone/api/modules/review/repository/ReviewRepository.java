package com.steamclone.api.modules.review.repository;

import com.steamclone.api.modules.game.dto.GameRatingProjection;
import com.steamclone.api.modules.review.entity.Review;
import com.steamclone.api.modules.game.entity.Game;
import com.steamclone.api.modules.user.entity.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    Page<Review> findByGame(Game game, Pageable pageable);

    Optional<Review> findByUserAndGame(User user, Game game);

    @Query("""
    SELECT 
        r.game.id as gameId,
        AVG(r.rating) as averageRating,
        COUNT(r.id) as totalReviews
    FROM Review r
    WHERE r.game.id IN :gameIds
    GROUP BY r.game.id
""")
    List<GameRatingProjection> getRatingsForGames(List<UUID> gameIds);
}