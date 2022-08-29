package hometoogether.hometoogether.config;

import hometoogether.hometoogether.domain.user.domain.BlackList;
import hometoogether.hometoogether.domain.user.domain.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, RefreshToken> refreshTokenRedisTemplate() {
        RedisTemplate<String, RefreshToken> refreshTokenRedisTemplate = new RedisTemplate<>();
        refreshTokenRedisTemplate.setConnectionFactory(redisConnectionFactory());
        refreshTokenRedisTemplate.setEnableTransactionSupport(true);
        return refreshTokenRedisTemplate;
    }

    @Bean
    public RedisTemplate<String, BlackList> blackListRedisTemplate() {
        RedisTemplate<String, BlackList> blackListRedisTemplate = new RedisTemplate<>();
        blackListRedisTemplate.setConnectionFactory(redisConnectionFactory());
        blackListRedisTemplate.setEnableTransactionSupport(true);
        return blackListRedisTemplate;
    }
}
