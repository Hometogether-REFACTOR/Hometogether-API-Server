package hometoogether.hometoogether.domain.post.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Challenge extends Post {
    @OneToMany(mappedBy = "challenge")
    private List<Trial> trials;

    public void addTrial(Trial trial) {
        this.getTrials().add(trial);
        trial.changeChallenge(this);
    }
}
