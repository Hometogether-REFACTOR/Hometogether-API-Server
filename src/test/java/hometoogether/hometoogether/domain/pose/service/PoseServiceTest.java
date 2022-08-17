package hometoogether.hometoogether.domain.pose.service;

import hometoogether.hometoogether.domain.pose.dto.UploadPoseReq;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import javax.transaction.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PoseServiceTest {

    @Autowired
    private PoseService poseService;

    @Test
    void S3_파일_업로드() throws IOException {
        //given
        User user = User.builder()
                .username("kim")
                .build();

        MockMultipartFile multipartFile = new MockMultipartFile("name", "originalFileName.png", "image/png", new FileInputStream("C:\\Users\\rlacksgus\\Desktop\\ddd.png"));
        UploadPoseReq uploadPoseReq = new UploadPoseReq();
        uploadPoseReq.setMultipartFile(multipartFile);

        //when
        //then
        String url = poseService.uploadPose(user, uploadPoseReq.getMultipartFile());
        System.out.println("url = " + url);
    }
}
