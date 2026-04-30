package com.steamclone.api.modules.game.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record GameResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        String genre,
        LocalDate releaseDate,

        Double ratingAverage,
        String ratingLabel,
        Long totalReviews
){
}
