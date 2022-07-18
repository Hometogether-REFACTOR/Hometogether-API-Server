package hometoogether.hometoogether.entity.user;

import hometoogether.hometoogether.entity.comment.CommentEntity;
import hometoogether.hometoogether.entity.heart.HeartEntity;
import hometoogether.hometoogether.entity.post.PostEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "userEntity")
    private List<PostEntity> postList;

    @OneToMany(mappedBy = "userEntity")
    private List<CommentEntity> commentList;

    @OneToMany(mappedBy = "userEntity")
    private List<HeartEntity> heartList;
}
