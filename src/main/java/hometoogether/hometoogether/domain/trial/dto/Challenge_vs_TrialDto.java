package hometoogether.hometoogether.domain.trial.dto;

import hometoogether.hometoogether.domain.trial.domain.Trial;
import lombok.Getter;

@Getter
public class Challenge_vs_TrialDto {
    private Long id;
    private String type;
    private String challenge_url;
    private String trial_url;
    private Double score;

    public Challenge_vs_TrialDto(Trial entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.challenge_url = entity.getChallenge().getChallengePose().getUrl();
        this.trial_url = entity.getTrialPose().getUrl();
        this.score = entity.getScore();
    }
}
