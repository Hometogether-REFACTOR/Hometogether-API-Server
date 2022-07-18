package hometoogether.hometoogether.domain.user.dto;

import lombok.Data;

@Data
public class LoginResDto {
    private String accessToken;
    private String refreshToken;
}
