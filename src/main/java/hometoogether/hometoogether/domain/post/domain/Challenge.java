package hometoogether.hometoogether.domain.post.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Challenge extends Post {
    @OneToMany(mappedBy = "challenge")
    private List<Trial> trialList;
}
