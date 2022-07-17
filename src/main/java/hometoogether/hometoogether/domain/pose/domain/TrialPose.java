package hometoogether.hometoogether.domain.pose.domain;

import hometoogether.hometoogether.domain.trial.domain.Trial;
import hometoogether.hometoogether.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "trial_pose")
public class TrialPose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String thumbnail_url;

    private String job_id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trial_pose")
    private List<Keypoints> keypointsList;

    public void addKeypoints(Keypoints keypoints) {
        keypoints.setTrial_pose(this);
        this.keypointsList.add(keypoints);
    }

    @ManyToOne
    private User user;

    @OneToOne
    private Trial trial;

    @Builder
    private TrialPose(String url, String thumbnail_url, List<Keypoints> keypointsList, User user, Trial trial) {
        this.url = url;
        this.thumbnail_url = thumbnail_url;
        this.keypointsList = keypointsList;
        this.user = user;
        this.trial = trial;
    }

    public void update(String job_id){
        this.job_id = job_id;
    }
}
