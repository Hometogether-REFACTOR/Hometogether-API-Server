package hometoogether.hometoogether.domain.pose.dto.kakaoApi;

import hometoogether.hometoogether.domain.pose.dto.kakaoApi.kakaoApiType.Annotation;
import hometoogether.hometoogether.domain.pose.dto.kakaoApi.kakaoApiType.Category;
import hometoogether.hometoogether.domain.pose.dto.kakaoApi.kakaoApiType.Info;
import hometoogether.hometoogether.domain.pose.dto.kakaoApi.kakaoApiType.Video;
import lombok.Data;

import java.util.List;

@Data
public class KakaoPoseVideoResultRes {
    private String job_id;
    private String status;
    private List<Annotation> annotations;
    private List<Category> categories;
    private Info info;
    private Video video;
    private String description;
}
