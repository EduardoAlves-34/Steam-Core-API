package com.steamclone.api.modules.purchase.entity;

import com.steamclone.api.modules.game.entity.Game;
import com.steamclone.api.modules.purchase.enums.PurchaseStatus;
import com.steamclone.api.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    private BigDecimal price;

    private LocalDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;
}