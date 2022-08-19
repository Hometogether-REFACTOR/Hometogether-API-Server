package hometoogether.hometoogether.domain.pose.service;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.domain.PoseType;
import hometoogether.hometoogether.domain.pose.dto.KakaoPoseVideoRes;
import hometoogether.hometoogether.domain.pose.dto.KakaoPoseVideoResultRes;
import hometoogether.hometoogether.domain.pose.repository.PoseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class KakaoServiceTest {

    @Autowired
    private KakaoService kakaoService;
    @Autowired
    private PoseRepository poseRepository;

    @Test
    void Kakao_이미지_테스트() {
        //given
        Pose pose = Pose.builder()
                .poseType(PoseType.PHOTO)
                .s3FileName("773e6039-33ab-453a-9b83-2576e34a0527.png")
                .build();
        poseRepository.save(pose);

        //when
        KakaoPoseVideoRes kakaoPoseVideoRes = kakaoService.kakaoPoseVideo(pose);

        //then
        System.out.println("kakaoPoseVideoRes.toString() = " + kakaoPoseVideoRes.toString());
    }

    @Test
    void Kakao_동영상_요청_테스트() {
        //given
        Pose pose = Pose.builder()
                .poseType(PoseType.PHOTO)
                .s3FileName("3b07b851-e7b1-429d-acf1-b31a9b6248e8.mp4")
                .build();
        poseRepository.save(pose);

        //when
        KakaoPoseVideoRes kakaoPoseVideoRes = kakaoService.kakaoPoseVideo(pose);

        //then
        System.out.println("kakaoPoseVideoRes.toString() = " + kakaoPoseVideoRes.toString());
    }

    @Test
    void Kakao_동영상_결과_테스트() {
        //given
        String job_id = "114f5503-6910-45c4-bc8b-1154cf48bfb8";

        //when
        KakaoPoseVideoResultRes kakaoPoseVideoResultRes = kakaoService.kakaoPoseVideoResult(job_id);

        //then
        System.out.println("kakaoPoseVideoResultRes.toString() = " + kakaoPoseVideoResultRes.toString());
    }
}
