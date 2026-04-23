package com.steamclone.api.modules.review.repository;

import com.steamclone.api.modules.review.entity.Review;
import com.steamclone.api.modules.game.entity.Game;
import com.steamclone.api.modules.user.entity.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    Page<Review> findByGame(Game game, Pageable pageable);

    Optional<Review> findByUserAndGame(User user, Game game);
}