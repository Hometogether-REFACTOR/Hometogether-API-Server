package hometoogether.hometoogether.domain.user.repository;

import hometoogether.hometoogether.domain.user.domain.RefreshToken;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RefreshTokenRepository {

    private static final String KEY = "RefreshToken";

    private final RedisTemplate<String, RefreshToken> refreshTokenRedisTemplate;
    private final HashOperations<String, String, RefreshToken> hashOperations;

    public RefreshTokenRepository(RedisTemplate<String, RefreshToken> refreshTokenRedisTemplate) {
        this.refreshTokenRedisTemplate = refreshTokenRedisTemplate;
        this.hashOperations = refreshTokenRedisTemplate.opsForHash();
    }

    public RefreshToken save(RefreshToken refreshToken) {
        String id = refreshToken.getId();
        hashOperations.put(KEY, id, refreshToken);
        return refreshToken;
    }

    public Optional<RefreshToken> findById(String id) {
        return Optional.ofNullable(hashOperations.get(KEY, id));
    }

    public void deleteById(String id) {
        hashOperations.delete(KEY, id);
    }
}
