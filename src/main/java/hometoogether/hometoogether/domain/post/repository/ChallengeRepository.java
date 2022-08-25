package hometoogether.hometoogether.domain.post.repository;

import hometoogether.hometoogether.domain.post.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    // 챌린지와 포즈와 유저를 fetch join 하는 쿼리
    @Query("select c from Challenge c join fetch c.pose p join fetch p.user u where c.id = :challengeId")
    Optional<Challenge> findChallengeById(@Param("challengeId") Long challengeId);
}
