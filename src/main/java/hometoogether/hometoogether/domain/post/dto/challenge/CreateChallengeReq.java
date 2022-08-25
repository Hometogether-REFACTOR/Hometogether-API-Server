package hometoogether.hometoogether.domain.post.dto.challenge;

import lombok.Data;

@Data
public class CreateChallengeReq {
    private Long poseId;
    private String title;
    private String content;
}
