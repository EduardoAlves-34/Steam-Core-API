package com.steamclone.api.modules.game.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GameRequest(
        String name,
        String description,
        BigDecimal price,
        String genre,
        LocalDate releaseDate
) {}
