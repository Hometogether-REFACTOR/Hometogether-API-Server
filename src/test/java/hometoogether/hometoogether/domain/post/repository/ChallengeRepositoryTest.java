package hometoogether.hometoogether.domain.post.repository;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.repository.PoseRepository;
import hometoogether.hometoogether.domain.post.domain.Challenge;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ChallengeRepositoryTest {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PoseRepository poseRepository;

    @Test
    void findChallengeById() {
        //given
        User user = User.builder()
                .username("kim")
                .build();
        userRepository.save(user);

        Pose pose = Pose.builder()
                .originalFileName("run")
                .user(user)
                .build();
        poseRepository.save(pose);

        Challenge challenge = Challenge.builder()
                .pose(pose)
                .title("hello")
                .build();
        challengeRepository.save(challenge);

        //when
        Challenge findChallenge = challengeRepository.findChallengeById(challenge.getId()).orElseThrow(() -> new RuntimeException("존재하지 않는 챌린지입니다."));

        //then
        Assertions.assertEquals(findChallenge.getPose().getUser().getUsername(), "kim");
    }
}
