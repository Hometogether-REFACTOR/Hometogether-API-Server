package hometoogether.hometoogether.domain.training.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TRAINING")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainingId;

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    // 필요한 루틴 정보 추가
    @Column(name="training_sec", nullable = false)
    private int trainingSec;

    @Column(name="training_set_cnt", nullable = false)
    private int trainingSetCnt;

    @Column(name = "seq", nullable = false)
    private int seq;

    @Builder
    public Training(Long trainingId, String trainingName, int trainingSec, int seq,
                    int trainingSetCnt) {
        this.trainingId = trainingId;
        this.trainingName = trainingName;
        this.trainingSec=trainingSec;
        this.seq=seq;
        this.trainingSetCnt=trainingSetCnt;
    }
}
