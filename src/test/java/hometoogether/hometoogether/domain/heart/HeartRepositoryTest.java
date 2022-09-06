package hometoogether.hometoogether.domain.heart;

import hometoogether.hometoogether.domain.post.domain.Challenge;
import hometoogether.hometoogether.domain.post.repository.ChallengeRepository;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Rollback(value = false)
class HeartRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private HeartRepository heartRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("UserId와 PostId로 Heart를 조회할 수 있다.")
    void findHeart() {
        // given
        User user = new User();
        userRepository.save(user);

        Challenge challenge = new Challenge();
        challengeRepository.save(challenge);

        Heart heart = heartRepository.save(Heart.builder()
                .user(user)
                .post(challenge)
                .build());

        em.flush();
        em.clear();

        // when
        Optional<Heart> findHeart = heartRepository.findByUserIdAndPostId(user.getId(), challenge.getId());

        // then
        assertDoesNotThrow(() -> new NoSuchElementException());
        assertEquals(heart.getId(), findHeart.get().getId());
    }
}