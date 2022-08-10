package hometoogether.hometoogether.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResDto {
    private String accessToken;
    private String refreshToken;
}
