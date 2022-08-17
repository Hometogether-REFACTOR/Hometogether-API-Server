package hometoogether.hometoogether.domain.pose.service;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.dto.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoService {

    private final String fileServerURL = "";
    private final String kakaoSecretKey = "";

    private WebClient webClient = WebClient
            .builder()
            .baseUrl("https://cv-api.kakaobrain.com")
            .defaultHeader("Authorization", kakaoSecretKey)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_FORM_URLENCODED))
            .build();

    public KakaoPosePhotoRes kakaoPosePhoto(Pose pose) {
        KakaoPosePhotoReq kakaoPosePhotoReq = new KakaoPosePhotoReq();
        kakaoPosePhotoReq.setImage_url(fileServerURL + pose.getS3FileName());

        return webClient.post()
                .uri("/pose")
                .bodyValue(kakaoPosePhotoReq)
                .retrieve()
                .bodyToMono(KakaoPosePhotoRes.class)
                .block();
    }

    public KakaoPoseVideoRes kakaoPoseVideo(Pose pose) {
        KakaoPoseVideoReq kakaoPoseVideoReq = new KakaoPoseVideoReq();
        kakaoPoseVideoReq.setVideo_url(fileServerURL + pose.getS3FileName());

        return webClient.post()
                .uri("/pose/job")
                .bodyValue(kakaoPoseVideoReq)
                .retrieve()
                .bodyToMono(KakaoPoseVideoRes.class)
                .block();
    }

    public KakaoPoseVideoResultRes kakaoPoseVideoResult(KakaoPoseVideoResultReq kakaoPoseVideoResultReq) {
        return webClient.get()
                .uri("/pose/job/" + kakaoPoseVideoResultReq.getJob_id())
                .retrieve()
                .bodyToMono(KakaoPoseVideoResultRes.class)
                .block();
    }
}
