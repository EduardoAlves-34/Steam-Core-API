package com.steamclone.api.modules.game.dto;

import java.util.UUID;

public interface GameRatingProjection {

    UUID getGameId();
    Double getAverageRating();
    Long getTotalReviews();

}