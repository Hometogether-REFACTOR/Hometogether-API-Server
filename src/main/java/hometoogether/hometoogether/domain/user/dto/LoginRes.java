package hometoogether.hometoogether.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRes {
    private String accessToken;
    private String refreshToken;
}
