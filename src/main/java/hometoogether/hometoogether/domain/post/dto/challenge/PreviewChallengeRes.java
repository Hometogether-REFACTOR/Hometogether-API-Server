package hometoogether.hometoogether.domain.post.dto.challenge;

import lombok.Data;

@Data
public class PreviewChallengeRes {
    private Long id;
    private String type;
    private String url;
    private String username;
    private String title;
}
