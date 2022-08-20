package hometoogether.hometoogether.domain.pose.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class PoseService {

    private final ObjectMapper objectMapper;

    private final FileService fileService;
    private final KakaoService kakaoService;
    private final PoseRepository poseRepository;

    public String uploadPose(User user, MultipartFile file) throws IOException {

        PoseType poseType;
        if (file.getContentType().startsWith("image")) {
            poseType = PoseType.PHOTO;
        } else if (file.getContentType().startsWith("video")) {
            poseType = PoseType.VIDEO;
        } else {
            throw new RuntimeException("지원하지 않는 파일 형식입니다.");
        }

        String s3FileName = fileService.upload(file);

        Pose pose = poseRepository.save(Pose.builder()
                .poseType(poseType)
                .originalFileName(file.getOriginalFilename())
                .s3FileName(s3FileName)
                .user(user)
                .build());

        estimatePose(pose);

        return s3FileName;
    }

    // TODO: 비동기 처리 구현
    @Async
    @Transactional
    public void estimatePose(Pose pose) throws JsonProcessingException {
        if (pose.getPoseType().equals(PoseType.PHOTO)) {
            estimatePosePhoto(pose);
        }
        else {
            estimatePoseVideo(pose);
        }
    }

    public void estimatePosePhoto(Pose pose) throws JsonProcessingException {
        KakaoPosePhotoRes kakaoPosePhotoRes = kakaoService.kakaoPosePhoto(pose);

        // TODO: 자세 정보 저장
        String poseDetail = objectMapper.writeValueAsString(kakaoPosePhotoRes);
        pose.changePoseDetail(poseDetail);
    }

    public void estimatePoseVideo(Pose pose) throws JsonProcessingException {
        KakaoPoseVideoRes kakaoPoseVideoRes = kakaoService.kakaoPoseVideo(pose);

        // TODO: 자세 분석이 완료될 때까지 폴링
        KakaoPoseVideoResultRes kakaoPoseVideoResultRes = kakaoService.kakaoPoseVideoResult(kakaoPoseVideoRes.getJob_id());
        while (kakaoPoseVideoResultRes.getStatus().startsWith("failed")) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            kakaoPoseVideoResultRes = kakaoService.kakaoPoseVideoResult(kakaoPoseVideoRes.getJob_id());
        }
        // TODO: 분석이 완료되면 자세 정보 저장
        String poseDetail = objectMapper.writeValueAsString(kakaoPoseVideoResultRes.getAnnotations());
        pose.changePoseDetail(poseDetail);

        // TODO: 자세 분석 완료 알림(SSE), 다중 WAS 환경 고려(Redis Pub/Sub)

    }

    public void estimatePoseVideoResult(KakaoPoseVideoResultReq kakaoPoseVideoResultReq) {
    }
}
