package hometoogether.hometoogether.domain.pose.service;

import hometoogether.hometoogether.domain.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;

import java.io.FileInputStream;

@SpringBootTest
class PoseServiceTest {

    @Autowired
    PoseService poseService;

    @Test
    void asyncTest() throws Exception {
        //given
        User user = User.builder()
                .username("kim")
                .build();

        MockMultipartFile multipartFile = new MockMultipartFile("name", "originalFileName.mp4", "video/mp4", new FileInputStream("C:\\Users\\rlacksgus\\Desktop\\1번자세.mp4"));

        //when
        poseService.uploadPose(user, multipartFile);

        //then
        System.out.println("PoseServiceTest.asyncTest finish");
    }

    @Async
    public void asyncFunc() {
        System.out.println("PoseServiceTest.asyncFunc start");

        for (int i=0; i<3; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("attempt i = " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("PoseServiceTest.asyncFunc finish");
    }
}
