package hometoogether.hometoogether.domain.user.domain;

import hometoogether.hometoogether.entity.comment.CommentEntity;
import hometoogether.hometoogether.entity.heart.HeartEntity;
import hometoogether.hometoogether.entity.post.PostEntity;
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

    @OneToMany(mappedBy = "userEntity")
    private List<PostEntity> postList;

    @OneToMany(mappedBy = "userEntity")
    private List<CommentEntity> commentList;

    @OneToMany(mappedBy = "userEntity")
    private List<HeartEntity> heartList;
}
