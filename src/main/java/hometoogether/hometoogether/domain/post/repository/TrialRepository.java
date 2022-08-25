package hometoogether.hometoogether.domain.post.repository;

import hometoogether.hometoogether.domain.post.domain.Trial;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrialRepository extends JpaRepository<Trial, Long> {
    // 시도와 포즈와 유저를 fetch join 하는 쿼리
    @Query("select t from Trial t join fetch Challenge c join fetch t.pose p join fetch p.user u where t.id = :trialId")
    Optional<Trial> findTrialById(@Param("trialId") Long trialId);

    @Query("select t from Trial t join fetch Challenge c join fetch t.pose p join fetch p.user u where c.id = :challengeId order by t.createdAt desc")
    List<Trial> findTrialAll(@Param("trialId") Long challengeId, Pageable pageable);
}
