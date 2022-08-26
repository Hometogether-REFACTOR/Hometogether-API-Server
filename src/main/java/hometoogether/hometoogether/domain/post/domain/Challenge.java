package hometoogether.hometoogether.domain.post.domain;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class Challenge extends Post {
    @ManyToOne(fetch = FetchType.LAZY)
    private Pose pose;

    public void changePose(Pose pose) {
        this.pose = pose;
    }
}
