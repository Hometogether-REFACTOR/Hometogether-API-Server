package hometoogether.hometoogether.domain.post.dto.trial;

import hometoogether.hometoogether.domain.post.domain.Trial;
import lombok.Data;

@Data
public class ReadTrialRes {
    private Long trialId;
    private String username;
    private String fileUrl;
    private String title;
    private String content;

    // 좋아요, 댓글 추가 예정

    public ReadTrialRes(Trial trial) {
        trialId = trial.getId();
        username = trial.getPose().getUser().getUsername();
        fileUrl = trial.getPose().getS3FileName();
        title = trial.getTitle();
        content = trial.getContent();
    }
}
