package hometoogether.hometoogether.domain.pose.service;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.domain.PoseType;
import hometoogether.hometoogether.domain.pose.dto.KakaoPosePhotoRes;
import hometoogether.hometoogether.domain.pose.dto.KakaoPoseVideoRes;
import hometoogether.hometoogether.domain.pose.dto.KakaoPoseVideoResultReq;
import hometoogether.hometoogether.domain.pose.dto.KakaoPoseVideoResultRes;
import hometoogether.hometoogether.domain.pose.repository.PoseRepository;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.util.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PoseService {

    private final FileService fileService;
    private final KakaoService kakaoService;
    private final PoseRepository poseRepository;

    @Transactional
    public String uploadPose(User user, MultipartFile file) throws IOException {

        PoseType poseType = null;
        if (file.getContentType().startsWith("image")) {
            poseType = PoseType.PHOTO;
        } else if (file.getContentType().startsWith("video")) {
            poseType = PoseType.VIDEO;
        }

        String s3FileName = fileService.upload(file);

        Pose pose = poseRepository.save(Pose.builder()
                .poseType(poseType)
                .originalFileName(file.getOriginalFilename())
                .s3FileName(s3FileName)
                .user(user)
                .build());

        // TODO: 비동기 처리 구현
//        estimatePose(pose);
        
        return s3FileName;
    }

    @Transactional
    public void estimatePose(Pose pose) {
        if (pose.getPoseType().equals(PoseType.PHOTO)) {
            estimatePosePhoto(pose);
        }
        else {
            // TODO: 비동기 처리 구현
            estimatePoseVideo(pose);
        }
    }

    @Transactional
    public void estimatePosePhoto(Pose pose) {
        KakaoPosePhotoRes kakaoPosePhotoRes = kakaoService.kakaoPosePhoto(pose);

        // TODO: 자세 정보 저장
    }

    @Async
    @Transactional
    public void estimatePoseVideo(Pose pose) {
        KakaoPoseVideoRes kakaoPoseVideoRes = kakaoService.kakaoPoseVideo(pose);

        // TODO: 자세 정보 저장
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

        // TODO: 자세 분석 완료 알림(SSE)
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