package hometoogether.hometoogether.domain.training.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class RoutineScoreDto {
    private Long routineId;
    private String userName;
    private String routineName;
    private float routineAvgScore;
    private int evaluateCnt;

    @Builder
    public RoutineScoreDto(Long routineId, String userName, String routineName, float routineAvgScore, int evaluateCnt) {
        this.routineId=routineId;
        this.userName = userName;
        this.routineName=routineName;
        this.routineAvgScore=routineAvgScore;
        this.evaluateCnt=evaluateCnt;
    }

//    public Routine toEntity(RoutineScoreDto routineScoreDto) {
//
//        return Routine.builder()
//                .userId(routineScoreDto.userId)
//                .routineName(routineScoreDto.routineName)
//                .routineAvgScore(routineScoreDto.routineAvgScore)
//                .evaluateCnt(routineScoreDto.evaluateCnt)
//                .build();
//    }
}
