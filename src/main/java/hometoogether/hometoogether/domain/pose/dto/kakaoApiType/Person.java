package hometoogether.hometoogether.domain.pose.dto.kakaoApiType;

import lombok.Data;

@Data
public class Person {
    private float area;
    private float[] bbox;
    private int category_id;
    private float[] keypoints;
    private float score;
}
