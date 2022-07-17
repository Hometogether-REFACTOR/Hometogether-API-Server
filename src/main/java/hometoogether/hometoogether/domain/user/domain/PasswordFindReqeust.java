package hometoogether.hometoogether.domain.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PasswordFindReqeust {
    private String email;
}
