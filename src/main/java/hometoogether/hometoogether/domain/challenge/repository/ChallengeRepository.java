package hometoogether.hometoogether.domain.challenge.repository;

import hometoogether.hometoogether.domain.challenge.domain.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findTop5ByOrderByTrialCountDesc();
    Page<Challenge> findAll(Pageable pageable);
}
