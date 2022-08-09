package hometoogether.hometoogether.domain.post.domain;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Challenge extends Post {
    @Builder
    public Challenge(Long id, User user, Pose pose, String title, String content) {
        super(id, user, pose, title, content);
    }
}
