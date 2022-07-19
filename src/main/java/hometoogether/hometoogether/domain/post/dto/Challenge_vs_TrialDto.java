package hometoogether.hometoogether.domain.post.dto;

import lombok.Getter;

@Getter
public class Challenge_vs_TrialDto {
    private Long id;
    private String type;
    private String challenge_url;
    private String trial_url;
    private Double score;
}
