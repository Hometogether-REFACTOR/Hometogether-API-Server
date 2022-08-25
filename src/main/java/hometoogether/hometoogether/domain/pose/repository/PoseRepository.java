package hometoogether.hometoogether.domain.pose.repository;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PoseRepository extends JpaRepository<Pose, Long> {
    // Pose와 User를 fetch join하는 쿼리
    @Query("select p from Pose p join fetch p.user u where p.id = :poseId")
    Optional<Pose> findPoseById(@Param("poseId") Long poseId);
}
