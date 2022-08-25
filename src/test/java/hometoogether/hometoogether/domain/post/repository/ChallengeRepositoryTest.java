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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        Assertions.assertEquals("kim", findChallenge.getPose().getUser().getUsername());
    }

    @Test
    @Transactional
    void findChallengeAll() throws InterruptedException {
        // given
        User user = User.builder()
                .username("kim")
                .build();
        userRepository.save(user);

        Pose pose = Pose.builder()
                .originalFileName("run")
                .user(user)
                .build();
        poseRepository.save(pose);

        for(int i=0; i<20; i++) {
            Challenge challenge = Challenge.builder()
                    .pose(pose)
                    .title("hello"+i)
                    .createdAt(LocalDateTime.now())
                    .build();
            Thread.sleep(5);
            System.out.println("i = " + i);
            challengeRepository.save(challenge);
        }

        //when
        Pageable pageable = PageRequest.of(0, 10);
        List<Challenge> challengeAll = challengeRepository.findChallengeAll(pageable);
        for (Challenge challenge : challengeAll) {
            System.out.println("challenge.getTitle() = " + challenge.getTitle());
        }

        //then
        Assertions.assertEquals("hello19", challengeAll.get(0).getTitle());
    }
}
