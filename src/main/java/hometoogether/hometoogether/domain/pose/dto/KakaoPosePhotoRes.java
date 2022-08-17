package hometoogether.hometoogether.domain.pose.dto.kakaoApi;

import lombok.Data;

@Data
public class KakaoPosePhotoRes {
    private float area;
    private float[] bbox;
    private int category_id;
    private float[] keypoints;
    private float score;
}
