package hometoogether.hometoogether.domain.user.repository;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.repository.PoseRepository;
import hometoogether.hometoogether.domain.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PoseRepository poseRepository;

    @Test
    @DisplayName("유저가 탈퇴하면 포즈도 삭제된다.")
    void withdraw() {
        //given
        User user = new User();

        Pose pose1 = new Pose();
        Pose pose2 = new Pose();

        user.addPose(pose1);
        user.addPose(pose2);

        userRepository.save(user);

        //when
        userRepository.delete(user);

        //then
        assertEquals(0, poseRepository.findAll().size());
    }
}
