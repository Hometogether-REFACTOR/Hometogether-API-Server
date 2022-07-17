package hometoogether.hometoogether.domain.challenge.dto;

import hometoogether.hometoogether.domain.challenge.domain.Challenge;
import lombok.Getter;

@Getter
public class ChallengeResponseDto {
    private Long id;
    private String type;
    private String url;
    private String username;
    private String title;

    public ChallengeResponseDto(Challenge entity) {

        this.id = entity.getId();
        this.type = entity.getType();
        if (entity.getType() == "video"){
            this.url = entity.getChallengePose().getThumbnail_url();
        }
        else{
            this.url = entity.getChallengePose().getUrl();
        }

        this.username = entity.getChallengePose().getUser().getUserName();
        this.title = entity.getTitle();
    }
}
