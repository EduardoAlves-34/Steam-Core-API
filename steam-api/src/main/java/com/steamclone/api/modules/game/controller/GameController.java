package com.steamclone.api.modules.game.controller;

import com.steamclone.api.modules.game.dto.GameRequest;
import com.steamclone.api.modules.game.dto.GameResponse;
import com.steamclone.api.modules.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/creategame")
    public ResponseEntity<GameResponse> createGame(@RequestBody GameRequest request) {
        GameResponse response = gameService.createGame(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/gamelist")
    public Page<GameResponse> list(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Pageable pageable
    ) {

        return gameService.listGames(
                genre, minPrice, maxPrice, pageable);
    }
}
