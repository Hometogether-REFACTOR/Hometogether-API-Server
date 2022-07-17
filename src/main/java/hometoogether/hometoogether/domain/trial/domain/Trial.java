package hometoogether.hometoogether.domain.trial.domain;

import hometoogether.hometoogether.domain.challenge.domain.Challenge;
import hometoogether.hometoogether.domain.pose.domain.TrialPose;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Trial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Double score = 0.0;

    @OneToOne
    private TrialPose trialPose;

    @ManyToOne
    private Challenge challenge;

    @Builder
    public Trial(TrialPose trialPose, Challenge challenge) {
//        this.type = challenge.getType();
        this.trialPose = trialPose;
        this.challenge = challenge;
    }
}
