package hometoogether.hometoogether.domain.pose.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.domain.PoseType;
import hometoogether.hometoogether.domain.pose.dto.KakaoPosePhotoRes;
import hometoogether.hometoogether.domain.pose.repository.PoseRepository;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.util.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PoseServiceTest {

    @InjectMocks
    PoseService poseService;
    @Mock
    FileService fileService;
    @Mock
    KakaoService kakaoService;
    @Mock
    PoseRepository poseRepository;
    @Mock
    ObjectMapper objectMapper;

    @Test
    @DisplayName("자세를 업로드하면 자세 분석 정보가 저장된다.")
    void uploadPose() throws Exception {
        //given
        User user = User.builder()
                .username("kim")
                .build();
        Pose pose = Pose.builder()
                .poseType(PoseType.PHOTO)
                .originalFileName("push-up")
                .build();
        MockMultipartFile multipartFile = new MockMultipartFile("name", "originalFileName.jpg", "image/jpg", new FileInputStream("C:\\Users\\rlacksgus\\Desktop\\1.jpg"));

        doReturn("fileURL").when(fileService).upload(any());
        doReturn(pose).when(poseRepository).save(any(Pose.class));
        doReturn(new KakaoPosePhotoRes()).when(kakaoService).kakaoPosePhoto(any());
        doReturn("poseDetailString").when(objectMapper).writeValueAsString(any());

        //when
        poseService.createPose(user, multipartFile);

        //then
        assertEquals("poseDetailString", pose.getPoseDetail());
    }
}
