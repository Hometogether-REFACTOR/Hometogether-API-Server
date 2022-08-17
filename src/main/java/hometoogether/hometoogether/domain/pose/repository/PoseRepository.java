package hometoogether.hometoogether.domain.pose.repository;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoseRepository extends JpaRepository<Pose, Long> {
}
