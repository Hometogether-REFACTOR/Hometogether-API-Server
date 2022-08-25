package hometoogether.hometoogether.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadProfileRes {
    private String nickname;
    private String introduction;
    private String profileImageURL;
}
