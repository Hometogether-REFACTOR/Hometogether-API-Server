package hometoogether.hometoogether.domain.post.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Trial extends Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;

    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;
}
