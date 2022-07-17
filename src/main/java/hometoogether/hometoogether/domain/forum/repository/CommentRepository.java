package hometoogether.hometoogether.domain.forum.repository;

import hometoogether.hometoogether.domain.forum.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByForumId(Long forumId);
    List<Comment> findCommentsByWriter(String userName);
    public void deleteCommentsByWriter(String userName);
}
