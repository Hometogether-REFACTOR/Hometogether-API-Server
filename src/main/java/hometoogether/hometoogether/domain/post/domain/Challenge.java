package hometoogether.hometoogether.domain.post.domain;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Challenge extends Post {

    @ManyToOne(fetch = FetchType.LAZY)
    private Pose pose;

    @OneToMany(mappedBy = "challenge")
    private List<Trial> trials;

    public void changePose(Pose pose) {
        this.pose = pose;
    }

    public void addTrial(Trial trial) {
        this.getTrials().add(trial);
        trial.changeChallenge(this);
    }
}
