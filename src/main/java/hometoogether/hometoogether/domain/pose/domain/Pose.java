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

    private String originalUrl;

    private String thumbnailUrl;

    private String job_id;

    private String keyPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
