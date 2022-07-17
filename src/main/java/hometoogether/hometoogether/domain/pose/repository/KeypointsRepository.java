package hometoogether.hometoogether.domain.pose.repository;

import hometoogether.hometoogether.domain.pose.domain.Keypoints;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeypointsRepository extends JpaRepository<Keypoints, Long> {
}
