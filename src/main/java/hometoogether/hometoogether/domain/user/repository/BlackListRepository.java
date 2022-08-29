package hometoogether.hometoogether.domain.user.repository;

import hometoogether.hometoogether.domain.user.domain.BlackList;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BlackListRepository {

    private static final String KEY = "BlackList";

    private final RedisTemplate<String, BlackList> blackListRedisTemplate;
    private final HashOperations<String, String, BlackList> hashOperations;

    public BlackListRepository(RedisTemplate<String, BlackList> blackListRedisTemplate) {
        this.blackListRedisTemplate = blackListRedisTemplate;
        this.hashOperations = blackListRedisTemplate.opsForHash();
    }

    public BlackList save(BlackList blackList) {
        String id = blackList.getId();
        hashOperations.put(KEY, id, blackList);
        return blackList;
    }
}
