package hometoogether.hometoogether.domain.user.dto;

import hometoogether.hometoogether.domain.user.domain.User;
import lombok.*;

@Data
public class JoinReqDto {
    private String username;
    private String password;

    public User toEntity(String password) {
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }
}
