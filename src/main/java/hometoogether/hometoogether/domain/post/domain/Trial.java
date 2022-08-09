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
public class Trial extends Post {
    private Double score;

    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge;

    @Builder
    public Trial(Long id, User user, Pose pose, String title, String content, Double score, Challenge challenge) {
        super(id, user, pose, title, content);
        this.score = score;
        this.challenge = challenge;
    }
}
