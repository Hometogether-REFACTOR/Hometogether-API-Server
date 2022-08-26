package hometoogether.hometoogether.domain.post.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Trial extends Post {

    private Double score;

    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;

    public void changeScore(double score) {
        this.score = score;
    }

    public void changeChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}
