package hometoogether.hometoogether.entity.pose;

import javax.persistence.Embeddable;

@Embeddable
public class Pose {
    private PoseType poseType;

    private String mediaUrl;

    private String thumbnailUrl;

    private String job_id;

    private String keypoints;
}
