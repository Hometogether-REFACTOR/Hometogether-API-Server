package hometoogether.hometoogether.domain.pose.domain;

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
public class Keypoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Double> keypoints;

    @ManyToOne
    @JoinColumn(name = "challenge_pose_id")
    private ChallengePose challenge_pose;

    @ManyToOne
    @JoinColumn(name = "trial_pose_id")
    private TrialPose trial_pose;

    @Builder
    public Keypoints(List<Double> keypoints){
        this.keypoints = keypoints;
    }

}
