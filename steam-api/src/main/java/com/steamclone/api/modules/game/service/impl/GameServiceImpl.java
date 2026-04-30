package com.steamclone.api.modules.game.service.impl;

import com.steamclone.api.modules.game.cache.GameCacheService;
import com.steamclone.api.modules.game.dto.GameRatingCache;
import com.steamclone.api.modules.game.dto.GameRatingProjection;
import com.steamclone.api.modules.game.dto.GameRequest;
import com.steamclone.api.modules.game.dto.GameResponse;
import com.steamclone.api.modules.game.entity.Game;
import com.steamclone.api.modules.game.enums.RatingLabel;
import com.steamclone.api.modules.game.repository.GameRepository;
import com.steamclone.api.modules.game.service.GameService;
import com.steamclone.api.modules.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ReviewRepository reviewRepository;
    private final GameCacheService gameCacheService;

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
                saved.getReleaseDate(),
                null,
                RatingLabel.fromAvg(null),
                0L
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
            page = gameRepository.findByPriceBetweenAndActiveTrue(minPrice, maxPrice, pageable);
        } else {
            page = gameRepository.findByActiveTrue(pageable);
        }

        List<UUID> gameIds = page.getContent()
                .stream()
                .map(Game::getId)
                .toList();

        Map<UUID, GameRatingCache> cacheMap = gameCacheService.getCachedRatings(gameIds);

        List<UUID> idsMissingInCache = gameIds.stream()
                .filter(id -> !cacheMap.containsKey(id))
                .toList();

        if (!idsMissingInCache.isEmpty()) {

            List<GameRatingProjection> ratingsFromDb = reviewRepository.getRatingsForGames(idsMissingInCache);

            ratingsFromDb.forEach(r -> {
                GameRatingCache newCache = new GameRatingCache(
                        r.getAverageRating(),
                        r.getTotalReviews(),
                        RatingLabel.fromAvg(r.getAverageRating())
                );
                gameCacheService.saveRating(r.getGameId(), newCache);
                cacheMap.put(r.getGameId(), newCache);
            });
        } else {
        }

        return page.map(game -> {
            GameRatingCache rating = cacheMap.get(game.getId());

            Double avg = (rating != null) ? rating.ratingAverage() : null;
            Long total = (rating != null) ? rating.totalReviews() : 0L;
            String label = (rating != null) ? rating.ratingLabel() : RatingLabel.fromAvg(null);

            return new GameResponse(
                    game.getId(),
                    game.getName(),
                    game.getDescription(),
                    game.getPrice(),
                    game.getGenre(),
                    game.getReleaseDate(),
                    avg,
                    label,
                    total
            );
        });
    }
}
