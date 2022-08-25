package hometoogether.hometoogether.domain.post.dto.trial;

import hometoogether.hometoogether.domain.post.domain.Trial;
import lombok.Data;

@Data
public class SimpleTrialRes {
    private Long id;
    private String fileUrl;
    private Double score;

    public SimpleTrialRes(Trial trial) {
        id = trial.getId();
        fileUrl = trial.getPose().getS3FileName();
        score = trial.getScore();
    }
}
