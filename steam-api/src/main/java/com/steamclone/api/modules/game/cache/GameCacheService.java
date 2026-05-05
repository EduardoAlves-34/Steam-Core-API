package com.steamclone.api.modules.game.cache;

import com.steamclone.api.modules.game.dto.GameRatingCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameCacheService {

    private static final String RATING_KEY_PREFIX = "game:ratings:";
    private static final Duration RATING_TTL = Duration.ofMinutes(10);

    private final RedisTemplate<String, GameRatingCache> redisTemplate;

    public Map<UUID, GameRatingCache> getCachedRatings(List<UUID> ids) {
        if (ids.isEmpty()) {
            return Map.of();
        }

        List<String> keys = ids.stream()
                .map(this::ratingKey)
                .toList();

        List<GameRatingCache> values = redisTemplate.opsForValue().multiGet(keys);

        Map<UUID, GameRatingCache> result = new HashMap<>();
        for (int i = 0; i < ids.size(); i++) {
            GameRatingCache val = values.get(i);
            if (val != null) {
                result.put(ids.get(i), val);
            }
        }
        return result;
    }

    public void saveRating(UUID gameId, GameRatingCache cache) {
        redisTemplate.opsForValue().set(ratingKey(gameId), cache, RATING_TTL);
    }

    public void evictRating(UUID gameId) {
        redisTemplate.delete(ratingKey(gameId));
    }

    private String ratingKey(UUID gameId) {
        return RATING_KEY_PREFIX + gameId;
    }
}
