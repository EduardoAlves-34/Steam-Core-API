package com.steamclone.api.modules.game.service.impl;

import com.steamclone.api.modules.game.dto.GameRequest;
import com.steamclone.api.modules.game.dto.GameResponse;
import com.steamclone.api.modules.game.entity.Game;
import com.steamclone.api.modules.game.repository.GameRepository;
import com.steamclone.api.modules.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public GameResponse createGame(GameRequest request) {

        Game game = new Game();
        game.setName(request.name());
        game.setDescription(request.description());
        game.setPrice(request.price());
        game.setGenre(request.genre());
        game.setReleaseDate(request.releaseDate());
        game.setActive(true);

        if(gameRepository.existsByNameIgnoreCase(request.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Game name already exists");
        }
            Game saved = gameRepository.save(game);

        return new GameResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getGenre(),
                saved.getReleaseDate()
        );
    }

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
