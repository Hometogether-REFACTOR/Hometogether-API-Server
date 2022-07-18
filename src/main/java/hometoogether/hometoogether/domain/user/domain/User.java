package hometoogether.hometoogether.domain.user.domain;

import hometoogether.hometoogether.domain.post.domain.Post;
import hometoogether.hometoogether.entity.comment.CommentEntity;
import hometoogether.hometoogether.entity.heart.HeartEntity;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @OneToMany(mappedBy = "user")
    private List<CommentEntity> commentList;

    @OneToMany(mappedBy = "user")
    private List<HeartEntity> heartList;

    public void addPost(Post post) {
        post.changeUser(this);
        this.postList.add(post);
    }
}
