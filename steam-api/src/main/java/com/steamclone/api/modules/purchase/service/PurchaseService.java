package com.steamclone.api.modules.purchase.service;

import com.steamclone.api.modules.purchase.dto.PurchaseHistoryResponse;
import com.steamclone.api.modules.purchase.dto.PurchaseResponse;
import com.steamclone.api.modules.purchase.enums.PurchaseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PurchaseService {
    PurchaseResponse purchaseGame(UUID gameId);

    Page<PurchaseHistoryResponse> getMyPurchaseHistory(PurchaseStatus status, Pageable pageable);
}
