package com.steamclone.api.modules.game.controller;

import com.steamclone.api.modules.game.dto.GameResponse;
import com.steamclone.api.modules.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
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
