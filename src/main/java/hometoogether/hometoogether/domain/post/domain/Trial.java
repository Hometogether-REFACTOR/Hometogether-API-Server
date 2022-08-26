package hometoogether.hometoogether.domain.post.domain;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trial extends Post {

    @ManyToOne(fetch = FetchType.LAZY)
    private Pose pose;

    private Double score;

    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;

    public void changePose(Pose pose) {
        this.pose = pose;
    }

    public void changeScore(double score) {
        this.score = score;
    }

    public void changeChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}
