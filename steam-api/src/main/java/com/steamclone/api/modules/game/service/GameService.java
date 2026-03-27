package com.steamclone.api.modules.game.service;

import com.steamclone.api.modules.game.dto.GameRequest;
import com.steamclone.api.modules.game.dto.GameResponse;
import org.springframework.data.domain.*;

import java.math.BigDecimal;

public interface GameService {

    Page<GameResponse> listGames(
            String genre,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

    GameResponse createGame(GameRequest request);

}
