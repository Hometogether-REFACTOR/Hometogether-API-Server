package hometoogether.hometoogether.domain.pose.dto.kakaoApiType;

import lombok.Data;

@Data
public class Annotation {
    private int frame_num;
    private Person[] objects;
}
