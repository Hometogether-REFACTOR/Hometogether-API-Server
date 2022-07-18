package hometoogether.hometoogether.domain.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ReadChallengeRes {
    private Long id;
    private String type;
    private String url;
    private String username;
    private String title;
    private String context;
    private List<String> trial_user_List;
}
