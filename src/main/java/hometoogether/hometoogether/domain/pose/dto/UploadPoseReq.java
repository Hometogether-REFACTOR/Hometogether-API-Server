package hometoogether.hometoogether.domain.pose.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadPoseReq {
    private MultipartFile multipartFile;
}
