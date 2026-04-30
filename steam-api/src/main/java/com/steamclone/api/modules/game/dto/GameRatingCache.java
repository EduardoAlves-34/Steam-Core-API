package com.steamclone.api.modules.game.dto;

import java.io.Serializable;

public record GameRatingCache(
        Double ratingAverage,
        Long totalReviews,
        String ratingLabel
) implements Serializable {
}