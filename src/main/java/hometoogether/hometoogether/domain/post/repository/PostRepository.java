package hometoogether.hometoogether.domain.post.repository;

import hometoogether.hometoogether.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
