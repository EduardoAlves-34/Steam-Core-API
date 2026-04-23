package com.steamclone.api.modules.review.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewResponse(
        UUID id,
        String username,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {}