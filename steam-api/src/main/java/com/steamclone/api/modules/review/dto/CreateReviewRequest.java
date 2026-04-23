package com.steamclone.api.modules.review.dto;

import jakarta.validation.constraints.*;

public record CreateReviewRequest(

        @Min(1)
        @Max(5)
        Integer rating,

        @Size(max = 1000)
        String comment
) {}