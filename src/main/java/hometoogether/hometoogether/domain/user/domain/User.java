package hometoogether.hometoogether.domain.user.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private String password;
}
