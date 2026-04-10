package com.steamclone.api.modules.purchase.dto;

import java.util.UUID;

public record PurchaseResponse(
        String message,
        UUID orderId,
        String status
) {}