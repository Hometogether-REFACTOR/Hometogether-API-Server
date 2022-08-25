package hometoogether.hometoogether.domain.post.dto.trial;

import lombok.Data;

@Data
public class UpdateTrialReq {
    private Long challengeId;
    private String title;
    private String content;
    private Long poseId;
}
