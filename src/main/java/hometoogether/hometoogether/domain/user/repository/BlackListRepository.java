package hometoogether.hometoogether.domain.user.repository;

import hometoogether.hometoogether.domain.user.domain.BlackList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BlackListRepository {

    private static final String KEY = "BlackList";

    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, BlackList> hashOperations;

    public BlackList save(BlackList blackList) {
        String id = blackList.getId();
        hashOperations.put(KEY, id, blackList);
        return blackList;
    }
}
