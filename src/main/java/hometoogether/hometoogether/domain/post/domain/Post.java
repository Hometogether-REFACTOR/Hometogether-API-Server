package hometoogether.hometoogether.domain.post.domain;

import hometoogether.hometoogether.domain.comment.Comment;
import hometoogether.hometoogether.domain.heart.Heart;
import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Embedded
    private Pose pose;

    private String title;

    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post")
    private List<Heart> heartList;

    public void changeUser(User user) {
        this.user = user;
    }
}
