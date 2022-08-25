package hometoogether.hometoogether.domain.post.dto.challenge;

import lombok.Data;

@Data
public class UpdateChallengeReq {
    private Long challengeId;
    private String title;
    private String content;
    private Long poseId;
}
