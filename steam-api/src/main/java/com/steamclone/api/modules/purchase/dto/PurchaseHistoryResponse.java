package com.steamclone.api.modules.purchase.dto;

import com.steamclone.api.modules.purchase.enums.PurchaseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PurchaseHistoryResponse(
        UUID id,
        UUID gameId,
        String gameName,
        BigDecimal price,
        LocalDateTime purchaseDate,
        PurchaseStatus status
) {}