package com.steamclone.api.modules.purchase.repository;

import com.steamclone.api.modules.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
}