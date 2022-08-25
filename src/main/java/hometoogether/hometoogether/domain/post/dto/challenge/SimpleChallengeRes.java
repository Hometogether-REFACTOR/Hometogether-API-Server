package hometoogether.hometoogether.domain.post.dto.challenge;

import hometoogether.hometoogether.domain.post.domain.Challenge;
import lombok.Data;

@Data
public class SimpleChallengeRes {
    private Long id;
    private String fileUrl;

    public SimpleChallengeRes(Challenge challenge) {
        id = challenge.getId();
        fileUrl = challenge.getPose().getS3FileName();
    }
}
