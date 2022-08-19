package hometoogether.hometoogether.domain.pose.dto.kakaoApiType;

import lombok.Data;

import java.util.List;

@Data
public class Category {
    private int id;
    private String[] keypoints;
    private String name;
    private List<int[]> skeleton;
    private String supercategory;
}
