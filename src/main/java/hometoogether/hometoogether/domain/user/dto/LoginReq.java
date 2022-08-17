package hometoogether.hometoogether.domain.user.dto;

import lombok.Data;

@Data
public class LoginReq {
    private String username;
    private String password;
}
