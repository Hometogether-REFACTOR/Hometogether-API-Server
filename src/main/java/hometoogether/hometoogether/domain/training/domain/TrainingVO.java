package hometoogether.hometoogether.domain.training.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class TrainingVO {
    private Long trainingId;
    private String trainingName;
    private int trainingSec;
    private int trainingSetCnt;
    private int seq;

    @Builder
    public TrainingVO(Long trainingId, String trainingName, int trainingSec, int trainingSetCnt, int seq) {
        this.seq = seq;
        this.trainingSec=trainingSec;
        this.trainingSetCnt=trainingSetCnt;
        this.trainingId = trainingId;
        this.trainingName = trainingName;
    }

    public Training toEntity(){
        return Training.builder()
                .trainingId(trainingId)
                .trainingName(trainingName)
                .trainingSetCnt(trainingSetCnt)
                .trainingSetCnt(trainingSetCnt)
                .seq(seq).build();
    }
}
