package hometoogether.hometoogether.domain.pose.domain;

import hometoogether.hometoogether.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Pose {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private PoseType poseType;

    private String originalFileName;

    private String s3FileName;

    private String job_id;

    private String poseDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void changePoseDetail(String poseDetail) {
        this.poseDetail = poseDetail;
    }
}
