package hometoogether.hometoogether.domain.user.domain;

import hometoogether.hometoogether.domain.pose.domain.ChallengePose;
import hometoogether.hometoogether.domain.pose.domain.TrialPose;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Setter
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "routine_id")
    private Long routineId;

    @Column(name = "forum_id")
    private Long forumId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany
    private List<ChallengePose> challengePoseList;

    public void addChallengePose(ChallengePose challengePose) {
        challengePose.setUser(this);
        this.challengePoseList.add(challengePose);
    }

    @OneToMany
    private List<TrialPose> trialPoseList;

    public void addTrialPose(TrialPose trialPose) {
        trialPose.setUser(this);
        this.trialPoseList.add(trialPose);
    }

    @Builder
    private User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void update(String password) {
        this.password = password;
    }

}
