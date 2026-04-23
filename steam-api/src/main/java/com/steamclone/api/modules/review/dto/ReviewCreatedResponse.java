package com.steamclone.api.modules.review.dto;

import java.time.LocalDateTime;

public record ReviewCreatedResponse(
        String gameTitle,
        String username,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {}
