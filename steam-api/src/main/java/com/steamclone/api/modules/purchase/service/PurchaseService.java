package com.steamclone.api.modules.purchase.service;

import com.steamclone.api.modules.purchase.dto.PurchaseResponse;

import java.util.UUID;

public interface PurchaseService {
    PurchaseResponse purchaseGame(UUID gameId);
}
