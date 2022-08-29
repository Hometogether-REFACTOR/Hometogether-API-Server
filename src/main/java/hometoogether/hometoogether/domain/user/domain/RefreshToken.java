package hometoogether.hometoogether.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class RefreshToken implements Serializable {
    private String id;
    private String refreshToken;
    @TimeToLive
    private Long timeToLive;
}
