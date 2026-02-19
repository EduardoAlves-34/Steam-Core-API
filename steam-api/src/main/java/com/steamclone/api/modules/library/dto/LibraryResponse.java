package com.steamclone.api.modules.library.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record LibraryResponse(
        UUID id,
        UUID gameId,
        LocalDateTime purchaseDate,
        Integer hoursPlayed,
        Boolean installed
) {

}
