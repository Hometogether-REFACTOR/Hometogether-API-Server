package hometoogether.hometoogether.domain.challenge.domain;

import hometoogether.hometoogether.domain.pose.domain.ChallengePose;
import hometoogether.hometoogether.domain.trial.domain.Trial;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @OneToOne
    private ChallengePose challengePose;

    private String title;

    private String context;

    @OneToMany
    private List<Trial> trialList;

    private Integer trialCount = 0;

    public void addTrial(Trial trial) {
        trial.setChallenge(this);
        this.trialList.add(trial);
        this.trialCount += 1;
    }

    @Builder
    public Challenge(ChallengePose challengePose, String title, String context, String type) {
        this.type = type;
        this.challengePose = challengePose;
        this.title = title;
        this.context = context;
    }

    public void update(ChallengePose challengePose, String title, String context) {
        this.challengePose = challengePose;
        this.title = title;
        this.context = context;
    }
}
