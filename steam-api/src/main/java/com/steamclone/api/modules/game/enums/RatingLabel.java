package com.steamclone.api.modules.game.enums;

import lombok.Getter;

@Getter
public enum RatingLabel {
    OVERWHELMINGLY_POSITIVE("Overwhelmingly Positive"),
    VERY_POSITIVE("Very Positive"),
    POSITIVE("Positive"),
    NEUTRAL("Neutral"),
    NEGATIVE("Negative"),
    NO_REVIEWS("No reviews");

    private final String description;

    RatingLabel(String description) {
        this.description = description;
    }

    public static String fromAvg(Double avg) {
        if (avg == null) {
            return NO_REVIEWS.getDescription();
        }

        if (avg >= 4.5) return OVERWHELMINGLY_POSITIVE.getDescription();
        if (avg >= 4.0) return VERY_POSITIVE.getDescription();
        if (avg >= 3.0) return POSITIVE.getDescription();
        if (avg >= 2.0) return NEUTRAL.getDescription();

        return NEGATIVE.getDescription();
    }
}