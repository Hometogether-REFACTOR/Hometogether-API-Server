package hometoogether.hometoogether.domain.post.dto.trial;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateTrialReq {
    private Long challengeId;
    private Long poseId;
    private String title;
    private String content;
}
