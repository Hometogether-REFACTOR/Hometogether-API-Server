package hometoogether.hometoogether.domain.training.domain;

import hometoogether.hometoogether.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="ROUTINE")
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routineId;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
    @Column(name = "user_name", nullable = false)
    private String userName;

    private String routineName;
    private float routineAvgScore;
    private int evaluateCnt;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Training> trainings;

    @Builder
    public Routine(String userName, String routineName, List<Training> trainings, float routineAvgScore, int evaluateCnt) {
        this.userName = userName;
        this.routineName=routineName;
        this.trainings=trainings;
        this.routineAvgScore=routineAvgScore;
        this.evaluateCnt=evaluateCnt;
    }
}
