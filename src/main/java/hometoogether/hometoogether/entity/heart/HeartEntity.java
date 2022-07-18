package hometoogether.hometoogether.entity.heart;

import hometoogether.hometoogether.entity.post.PostEntity;
import hometoogether.hometoogether.entity.user.UserEntity;

import javax.persistence.*;

@Entity
public class HeartEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity postEntity;
}
