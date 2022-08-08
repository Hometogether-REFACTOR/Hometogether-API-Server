package hometoogether.hometoogether.domain.comment;

import hometoogether.hometoogether.domain.post.domain.Post;
import hometoogether.hometoogether.domain.user.domain.User;

import javax.persistence.*;

@Entity
public class Comment {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
