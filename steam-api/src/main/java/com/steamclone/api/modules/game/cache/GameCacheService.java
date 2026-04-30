package com.steamclone.api.modules.game.cache;

import com.steamclone.api.modules.game.dto.GameRatingCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String HASH_KEY = "game:ratings";

    public Map<UUID, GameRatingCache> getCachedRatings(List<UUID> ids) {
        List<Object> fields = ids.stream()
                .map(UUID::toString)
                .collect(Collectors.toList());

        List<Object> values = redisTemplate.opsForHash().multiGet(HASH_KEY, fields);

        Map<UUID, GameRatingCache> result = new HashMap<>();
        for (int i = 0; i < ids.size(); i++) {
            Object val = values.get(i);
            if (val != null) {
                result.put(ids.get(i), (GameRatingCache) val);
            }
        }
        return result;
    }

    public void saveRating(UUID gameId, GameRatingCache cache) {
        redisTemplate.opsForHash().put(HASH_KEY, gameId.toString(), cache);
    }

    public void evictRating(UUID gameId) {
        redisTemplate.opsForHash().delete(HASH_KEY, gameId.toString());
    }
}