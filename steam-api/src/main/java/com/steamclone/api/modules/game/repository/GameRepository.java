package com.steamclone.api.modules.game.repository;

import com.steamclone.api.modules.game.entity.Game;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    Page<Game> findByActiveTrue(Pageable pageable);

    Page<Game> findByGenreAndActiveTrue(String genre, Pageable pageable);

    Page<Game> findByPriceBetweenAndActiveTrue(
            BigDecimal min,
            BigDecimal max,
            Pageable pageable
    );
}
