package hometoogether.hometoogether.domain.post.repository;

import hometoogether.hometoogether.domain.post.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
