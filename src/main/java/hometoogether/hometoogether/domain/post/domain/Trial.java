package hometoogether.hometoogether.domain.post.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Trial extends Post {
    private Double score;

    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;
}
