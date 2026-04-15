package com.steamclone.api.modules.purchase.controller;

import com.steamclone.api.modules.purchase.dto.PurchaseHistoryResponse;
import com.steamclone.api.modules.purchase.dto.PurchaseResponse;
import com.steamclone.api.modules.purchase.enums.PurchaseStatus;
import com.steamclone.api.modules.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/{gameId}")
    public ResponseEntity<PurchaseResponse> purchaseGame(@PathVariable UUID gameId) {
        PurchaseResponse response = purchaseService.purchaseGame(gameId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/history")
    public Page<PurchaseHistoryResponse> getMyPurchaseHistory(
            @RequestParam(required = false) PurchaseStatus status,
            Pageable pageable
    ) {
        return purchaseService.getMyPurchaseHistory(status, pageable);
    }
}
