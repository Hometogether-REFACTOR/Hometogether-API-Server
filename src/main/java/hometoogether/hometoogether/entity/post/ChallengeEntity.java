package hometoogether.hometoogether.entity.post;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ChallengeEntity extends PostEntity {
    @OneToMany(mappedBy = "challengeEntity")
    private List<TrialEntity> trialList;
}
