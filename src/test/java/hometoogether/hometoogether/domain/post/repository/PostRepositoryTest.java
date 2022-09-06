package hometoogether.hometoogether.domain.post.repository;

import hometoogether.hometoogether.domain.post.domain.Challenge;
import hometoogether.hometoogether.domain.post.domain.Post;
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
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("챌린지를 저장하고 PostRepository에서 조회가 가능하다")
    void saveAndFind() {
        // given
        Challenge challenge = new Challenge();
        challengeRepository.save(challenge);

        em.flush();
        em.clear();

        // when
        Optional<Post> post = postRepository.findById(challenge.getId());

        // then
        assertDoesNotThrow(() -> new NoSuchElementException());
        assertEquals(challenge.getId(), post.get().getId());
    }
}