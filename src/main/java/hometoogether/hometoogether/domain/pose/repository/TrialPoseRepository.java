package hometoogether.hometoogether.domain.pose.repository;

import hometoogether.hometoogether.domain.pose.domain.TrialPose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrialPoseRepository extends JpaRepository<TrialPose, Long> {
}
