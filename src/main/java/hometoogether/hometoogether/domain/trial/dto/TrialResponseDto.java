package hometoogether.hometoogether.domain.trial.dto;

import hometoogether.hometoogether.domain.trial.domain.Trial;
import lombok.Getter;

@Getter
public class TrialResponseDto {
    private Long id;
    private String type;
    private String url;
    private String username;
    private Double score;

    public TrialResponseDto(Trial entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.url = entity.getTrialPose().getUrl();
        this.username = entity.getTrialPose().getUser().getUserName();
        this.score = entity.getScore();
    }
}
