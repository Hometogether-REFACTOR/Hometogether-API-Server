package hometoogether.hometoogether.entity.post;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class TrialEntity extends PostEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private ChallengeEntity challengeEntity;
}
