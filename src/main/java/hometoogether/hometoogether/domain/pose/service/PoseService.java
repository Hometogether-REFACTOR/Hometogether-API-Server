package hometoogether.hometoogether.domain.pose.service;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.domain.PoseType;
import hometoogether.hometoogether.domain.pose.dto.KakaoPosePhotoRes;
import hometoogether.hometoogether.domain.pose.dto.KakaoPoseVideoRes;
import hometoogether.hometoogether.domain.pose.dto.KakaoPoseVideoResultReq;
import hometoogether.hometoogether.domain.pose.dto.KakaoPoseVideoResultRes;
import hometoogether.hometoogether.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PoseService {

    private final KakaoService kakaoService;
    private final PostRepository postRepository;

    @Transactional
    public void estimatePose(Pose pose) {
        if (pose.getPoseType().equals(PoseType.PHOTO)) {
            estimatePosePhoto(pose);
        }
        else {
            estimatePoseVideo(pose);
        }
    }

    @Async
    @Transactional
    public void estimatePosePhoto(Pose pose) {
        KakaoPosePhotoRes kakaoPosePhotoRes = kakaoService.kakaoPosePhoto(pose);
    }

    @Async
    @Transactional
    public void estimatePoseVideo(Pose pose) {
        KakaoPoseVideoRes kakaoPoseVideoRes = kakaoService.kakaoPoseVideo(pose);

//        try {
//            Thread.sleep(60000);
//            estimatePoseDetailVideo(job_id, pose_id, pose_type);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Transactional
    public void estimatePoseVideoResult(KakaoPoseVideoResultReq kakaoPoseVideoResultReq) {
        KakaoPoseVideoResultRes kakaoPoseVideoResultRes = kakaoService.kakaoPoseVideoResult(kakaoPoseVideoResultReq);
    }

    //    public String test(String url) throws IOException {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.add("Authorization", "KakaoAK 19a4097fe8917a985bb1a7acc9ce2fb1");
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
////        String url = "https://preview.clipartkorea.co.kr/2016/05/27/ti325057081.jpg";
////        String url = "https://preview.clipartkorea.co.kr/2015/03/20/tip034z15020088.jpg";
////        String url = "http://preview.clipartkorea.co.kr/2016/05/27/ti325057171.jpg";
////        String url = "https://media.istockphoto.com/photos/looking-at-camera-front-view-full-length-one-person-of-2029-years-old-picture-id1182145935";
//
//        params.add("image_url", url);
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
//
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://cv-api.kakaobrain.com/pose",
//                HttpMethod.POST,
//                entity,
//                String.class
//                );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonbody = response.getBody();
//        PoseInfo poseInfo = objectMapper.readValue(jsonbody, PoseInfo.class);
//        poseInfoRepository.save(poseInfo);
//
//        Pose pose = Pose.builder()
//                .url(url)
//                .poseInfo(poseInfo)
//                .build();
//        poseRepository.save(pose);
//
//        System.out.println("response = " + response);
//        return jsonbody;
//    }


}