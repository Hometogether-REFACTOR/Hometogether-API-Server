package hometoogether.hometoogether.domain.user.domain;

import hometoogether.hometoogether.domain.comment.Comment;
import hometoogether.hometoogether.domain.heart.Heart;
import hometoogether.hometoogether.domain.post.domain.Post;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user")
    private List<Heart> heartList;

    public void addPost(Post post) {
        post.changeUser(this);
        this.postList.add(post);
    }
}
