package com.steamclone.api.modules.purchase.repository;

import com.steamclone.api.modules.purchase.entity.Purchase;
import com.steamclone.api.modules.purchase.enums.PurchaseStatus;
import com.steamclone.api.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    Page<Purchase> findByUser(User user, Pageable pageable);

    Page<Purchase> findByUserAndStatus(
            User user,
            PurchaseStatus status,
            Pageable pageable
    );
}