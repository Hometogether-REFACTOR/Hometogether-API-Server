package hometoogether.hometoogether.domain.post.domain;

import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.entity.comment.CommentEntity;
import hometoogether.hometoogether.entity.heart.HeartEntity;
import hometoogether.hometoogether.entity.pose.Pose;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
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
    private List<CommentEntity> commentList;

    @OneToMany(mappedBy = "post")
    private List<HeartEntity> heartList;
}
