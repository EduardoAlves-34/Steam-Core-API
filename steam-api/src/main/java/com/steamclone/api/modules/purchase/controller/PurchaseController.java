package com.steamclone.api.modules.purchase.controller;

import com.steamclone.api.modules.purchase.dto.PurchaseResponse;
import com.steamclone.api.modules.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
