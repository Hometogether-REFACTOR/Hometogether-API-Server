package hometoogether.hometoogether.domain.training.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class RoutineDto {
    private Long routineId;
    private String userName;
    private String routineName;
    private float avgScore;
    private int evalCnt;

    @Builder
    public RoutineDto(Long routineId, String userName, String routineName, float avgScore, int evalCnt) {
        this.routineId=routineId;
        this.userName = userName;
        this.routineName=routineName;
        this.avgScore=avgScore;
        this.evalCnt=evalCnt;
    }

//    public Routine toEntity(RoutineDto routineDto) {
////        Routine entity=new Routine();
////        List<Training> trainingList=new ArrayList<>();
//
////        for (TrainingVO t : routineDto.getTrainings()) {
////            Training training = t.toEntity();
////            trainingList.add(training);
////        }
//
//        return Routine.builder()
//                .user(user)
//                .routineName(routineName)
////                .trainings(trainingList)
//                .build();
//    }
}
