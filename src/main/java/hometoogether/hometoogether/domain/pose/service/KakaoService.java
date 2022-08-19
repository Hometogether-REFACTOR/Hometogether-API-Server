package hometoogether.hometoogether.domain.pose.service;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.dto.*;
import hometoogether.hometoogether.util.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${kakao.appKey}")
    private String kakaoAppKey;

    private WebClient webClient = WebClient
            .builder()
            .baseUrl("https://cv-api.kakaobrain.com")
            .build();

    private final FileService fileService;

    public KakaoPosePhotoRes kakaoPosePhoto(Pose pose) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("image_url", fileService.getOriginalFileUrl(pose.getS3FileName()));

        return webClient.post()
                .uri("/pose")
                .header("Authorization", kakaoAppKey)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<KakaoPosePhotoRes>>() {})
                .block()
                .get(0);
    }

    public KakaoPoseVideoRes kakaoPoseVideo(Pose pose) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("video_url", fileService.getOriginalFileUrl(pose.getS3FileName()));

        return webClient.post()
                .uri("/pose/job")
                .header("Authorization", kakaoAppKey)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(KakaoPoseVideoRes.class)
                .block();
    }

    public KakaoPoseVideoResultRes kakaoPoseVideoResult(String job_id) {
        return webClient.get()
                .uri("/pose/job/" + job_id)
                .header("Authorization", kakaoAppKey)
                .retrieve()
                .bodyToMono(KakaoPoseVideoResultRes.class)
                .block();
    }
}
