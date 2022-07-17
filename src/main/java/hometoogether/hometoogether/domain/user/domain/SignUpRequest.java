package hometoogether.hometoogether.domain.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class SignUpRequest {
    private String email;
    private String userName;
    private String password;

    public User toEntity() {
        return User.builder()
                .email(email)
                .userName(userName)
                .password(password)
                .build();
    }
}
