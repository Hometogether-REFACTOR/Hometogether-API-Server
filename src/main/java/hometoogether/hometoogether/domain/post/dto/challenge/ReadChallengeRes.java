package hometoogether.hometoogether.domain.post.dto.challenge;

import hometoogether.hometoogether.domain.post.domain.Challenge;
import lombok.Data;

@Data
public class ReadChallengeRes {
    private Long challengeId;
    private String username;
    private String fileUrl;
    private String title;
    private String content;

    // 좋아요, 댓글, Trial 수 추가 예정

    public ReadChallengeRes(Challenge challenge) {
        challengeId = challenge.getId();
        username = challenge.getPose().getUser().getUsername();
        fileUrl = challenge.getPose().getS3FileName();
        title = challenge.getTitle();
        content = challenge.getContent();
    }
}
