package hometoogether.hometoogether.domain.post.dto.challenge;

import hometoogether.hometoogether.domain.pose.PoseType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateChallengeReq {
    private PoseType poseType;
    private String title;
    private String content;
    private MultipartFile file;
}
