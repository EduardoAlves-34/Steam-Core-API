package com.steamclone.api.modules.purchase.service.impl;

import com.steamclone.api.modules.game.entity.Game;
import com.steamclone.api.modules.game.repository.GameRepository;
import com.steamclone.api.modules.library.entity.Library;
import com.steamclone.api.modules.library.repository.LibraryRepository;
import com.steamclone.api.modules.purchase.entity.Purchase;
import com.steamclone.api.modules.purchase.enums.PurchaseStatus;
import com.steamclone.api.modules.purchase.repository.PurchaseRepository;
import com.steamclone.api.modules.purchase.service.PurchaseService;
import com.steamclone.api.modules.user.entity.User;
import com.steamclone.api.modules.user.repository.UserRepository;
import com.steamclone.api.shared.exception.BusinessException;
import com.steamclone.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final PurchaseRepository purchaseRepository;
    private final LibraryRepository libraryRepository;

    @Override
    public void purchaseGame(UUID gameId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Jogo não encontrado"));

        if (libraryRepository.existsByUserAndGame(user, game)) {
            throw new BusinessException("Você já possui esse jogo");
        }

        Purchase purchase = Purchase.builder()
                .id(UUID.randomUUID())
                .user(user)
                .game(game)
                .price(game.getPrice())
                .purchaseDate(LocalDateTime.now())
                .status(PurchaseStatus.APPROVED)
                .build();

        purchaseRepository.save(purchase);

        Library library = Library.builder()
                .user(user)
                .game(game)
                .purchaseDate(LocalDateTime.now())
                .hoursPlayed(0)
                .installed(false)
                .build();

        libraryRepository.save(library);
    }
}