package hometoogether.hometoogether.domain.trial.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@Data
public class TrialRequestDto {
    private MultipartFile file;
    private String userName;
}
