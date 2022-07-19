package hometoogether.hometoogether.domain.pose;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Builder
@NoArgsConstructor
@Embeddable
public class Pose {
    @Enumerated(value = EnumType.STRING)
    private PoseType poseType;

    private String originalUrl;

    private String thumbnailUrl;

    private String job_id;

    private String keyPoints;
}
