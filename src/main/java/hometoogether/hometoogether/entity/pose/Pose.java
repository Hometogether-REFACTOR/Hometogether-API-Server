package hometoogether.hometoogether.entity.pose;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Pose {
    @Enumerated(value = EnumType.STRING)
    private PoseType poseType;

    private String mediaUrl;

    private String thumbnailUrl;

    private String job_id;

    private String keypoints;
}
