package hometoogether.hometoogether.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProfileReq {
    private String nickname;
    private String introduction;
    private String profileImageURL;
}
