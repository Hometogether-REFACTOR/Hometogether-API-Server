package hometoogether.hometoogether.entity.post;

import hometoogether.hometoogether.entity.comment.CommentEntity;
import hometoogether.hometoogether.entity.heart.HeartEntity;
import hometoogether.hometoogether.entity.pose.Pose;
import hometoogether.hometoogether.entity.user.UserEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class PostEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @Embedded
    private Pose pose;

    private String title;

    private String content;

    @OneToMany(mappedBy = "postEntity")
    private List<CommentEntity> commentList;

    @OneToMany(mappedBy = "postEntity")
    private List<HeartEntity> heartList;
}
