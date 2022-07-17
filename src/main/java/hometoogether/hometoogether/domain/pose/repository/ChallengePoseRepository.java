package hometoogether.hometoogether.domain.pose.repository;

import hometoogether.hometoogether.domain.pose.domain.ChallengePose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengePoseRepository extends JpaRepository<ChallengePose, Long> {
}
