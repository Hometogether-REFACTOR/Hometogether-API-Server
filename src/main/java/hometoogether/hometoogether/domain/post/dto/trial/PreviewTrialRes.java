package hometoogether.hometoogether.domain.post.dto.trial;

import lombok.Data;

@Data
public class PreviewTrialRes {
    private Long id;
    private String type;
    private String url;
    private String username;
    private Double score;
}
