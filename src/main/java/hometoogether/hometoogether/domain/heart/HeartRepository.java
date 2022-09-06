package hometoogether.hometoogether.domain.heart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByUserIdAndPostId(Long userId, Long postId);
}
