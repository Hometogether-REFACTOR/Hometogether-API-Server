package hometoogether.hometoogether.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginReq {
    private String username;
    private String password;
}
