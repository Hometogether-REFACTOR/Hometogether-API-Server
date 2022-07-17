package hometoogether.hometoogether.domain.forum.repository;

import hometoogether.hometoogether.domain.forum.domain.forum.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    public List<Forum> findForumsByWriter(String userName);
    public void deleteForumsByWriter(String userName);
}
