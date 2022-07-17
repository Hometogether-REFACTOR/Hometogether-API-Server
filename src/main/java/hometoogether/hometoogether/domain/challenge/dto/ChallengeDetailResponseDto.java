package hometoogether.hometoogether.domain.challenge.dto;

import hometoogether.hometoogether.domain.challenge.domain.Challenge;
import hometoogether.hometoogether.domain.trial.domain.Trial;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChallengeDetailResponseDto {
    private Long id;
    private String type;
    private String url;
    private String username;
    private String title;
    private String context;
    private List<String> trial_user_List;

    public ChallengeDetailResponseDto(Challenge entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.url = entity.getChallengePose().getUrl();
        this.username = entity.getChallengePose().getUser().getUserName();
        this.title = entity.getTitle();
        this.context = entity.getContext();
        if(!entity.getTrialList().isEmpty()){
            List<String> trial_user_List = new ArrayList<>();
            for (Trial t : entity.getTrialList()){
                trial_user_List.add(t.getTrialPose().getUser().getUserName());
            }
            this.trial_user_List = trial_user_List;
        }
    }
}
