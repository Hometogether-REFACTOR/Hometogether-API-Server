package hometoogether.hometoogether.domain.user.dto;

import hometoogether.hometoogether.domain.user.domain.User;
import lombok.*;

@Data
@AllArgsConstructor
public class JoinReq {
    private String username;
    private String password;
    private String nickname;

    public User toEntity(String saltedPassword, String salt) {
        return User.builder()
                .username(this.username)
                .password(saltedPassword)
                .nickname(this.nickname)
                .salt(salt)
                .build();
    }
}
