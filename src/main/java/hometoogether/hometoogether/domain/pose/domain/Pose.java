package hometoogether.hometoogether.domain.pose.domain;

import hometoogether.hometoogether.domain.post.domain.Challenge;
import hometoogether.hometoogether.domain.post.domain.Trial;
import hometoogether.hometoogether.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pose {

    private final String DELETED_FILE_URL = "삭제된 이미지";

    @Id @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private PoseType poseType;

    private String originalFileName;

    private String s3FileName;

    private String jobId;

    private String poseDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "pose")
    private List<Challenge> challenges = new ArrayList<>();

    @OneToMany(mappedBy = "pose")
    private List<Trial> trials = new ArrayList<>();

    public void changeUser(User user) {
        this.user = user;
    }

    public void changeJobId(String jobId) {
        this.jobId = jobId;
    }

    public void changePoseDetail(String poseDetail) {
        this.poseDetail = poseDetail;
    }

    public void addChallenge(Challenge challenge) {
        this.getChallenges().add(challenge);
        challenge.changePose(this);
    }

    public void addTrial(Trial trial) {
        this.getTrials().add(trial);
        trial.changePose(this);
    }
}
