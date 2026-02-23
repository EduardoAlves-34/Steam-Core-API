package com.steamclone.api.modules.game.service.impl;

import com.steamclone.api.modules.game.dto.GameResponse;
import com.steamclone.api.modules.game.entity.Game;
import com.steamclone.api.modules.game.repository.GameRepository;
import com.steamclone.api.modules.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public Page<GameResponse> listGames(
            String genre,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    ) {

        Page<Game> page;

        if (genre != null) {
            page = gameRepository.findByGenreAndActiveTrue(genre, pageable);
        } else if (minPrice != null && maxPrice != null) {
            page = gameRepository.findByPriceBetweenAndActiveTrue(
                    minPrice, maxPrice, pageable);
        } else {
            page = gameRepository.findByActiveTrue(pageable);
        }

        return page.map(game -> new GameResponse(
                game.getId(),
                game.getName(),
                game.getDescription(),
                game.getPrice(),
                game.getGenre(),
                game.getReleaseDate()
        ));
    }
}
