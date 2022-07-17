package hometoogether.hometoogether.domain.training.service;

import hometoogether.hometoogether.domain.training.domain.*;
import hometoogether.hometoogether.domain.training.repository.RoutineRepository;
import hometoogether.hometoogether.domain.training.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final TrainingRepository trainingRepository;

    public List<RoutineDto> getRoutines() {
        List<Routine> all = routineRepository.findAll();
        List<RoutineDto> result=new ArrayList<>();

        for(Routine routine : all){

            RoutineDto build = RoutineDto.builder()
                    .routineId(routine.getRoutineId())
                    .routineName(routine.getRoutineName())
                    .userName(routine.getUserName())
                    .avgScore(routine.getRoutineAvgScore())
                    .evalCnt(routine.getEvaluateCnt())
                    .build();
            result.add(build);
        }

        return result;
    }

    @Transactional
    public List<TrainingVO> getTrainingList(Long routineId) {
        Routine routine = routineRepository.findById(routineId).orElse(null);
        List<Training> trainings = routine.getTrainings();
        List<TrainingVO> trainingVOs=new ArrayList<>();

        for (Training training : trainings) {
            TrainingVO build = TrainingVO.builder()
                    .trainingId(training.getTrainingId())
                    .trainingName(training.getTrainingName())
                    .trainingSec(training.getTrainingSec())
                    .trainingSetCnt(training.getTrainingSetCnt())
                    .build();
            trainingVOs.add(build);
        }

        return trainingVOs;
    }

    @Transactional
    public void saveRoutine(Map<String, Object> routine) {
        List<Training> trainings=new ArrayList<>();
        Routine myRoutine=new Routine();

        String routineName = routine.get("routineName").toString();

        String trainingName = routine.get("trainingName").toString();
        String trainingSec = routine.get("trainingSec").toString();
        String trainingSetCnt = routine.get("trainingSetCnt").toString();
        String trainingSeq = routine.get("seq").toString();
        String userName=routine.get("userName").toString();

        String[] nameArray = (String[]) arrayParse(trainingName);
        String[] secArray = (String[]) arrayParse(trainingSec);
        String[] setCntArray = (String[]) arrayParse(trainingSetCnt);
        String[] seqArray = (String[]) arrayParse(trainingSeq);

        for(int i=0;i<nameArray.length;i++){
            Training training = Training.builder()
                    .trainingName(nameArray[i])
                    .trainingSec(Integer.valueOf(secArray[i]))
                    .trainingSetCnt(Integer.valueOf(setCntArray[i]))
                    .seq(Integer.valueOf(seqArray[i])).build();

            trainings.add(training);
            trainingRepository.save(training);
        }

        myRoutine.setTrainings(trainings);
        myRoutine.setRoutineName(routineName);
        myRoutine.setUserName(userName);
        myRoutine.setRoutineAvgScore(0);
        myRoutine.setEvaluateCnt(0);

        routineRepository.save(myRoutine);

    }

    @Transactional
    public float editRoutineAvgScore(Long routineId, Map<String, String> evaluation){
        System.out.println("RoutineService.editRoutineAvgScore");
        Routine routine = routineRepository.findById(routineId).orElse(null);
        Float currentEvaluateScore = Float.valueOf(evaluation.get("evaluateScore"));

        float routineAvgScore = routine.getRoutineAvgScore();
        int evaluateCnt = routine.getEvaluateCnt();

        routineAvgScore=(routineAvgScore*evaluateCnt+currentEvaluateScore)/(evaluateCnt+1);
        routineAvgScore = (float) (Math.floor(routineAvgScore * 100) / 100.0);

        routine.setRoutineAvgScore(routineAvgScore);
        routine.setEvaluateCnt(evaluateCnt+1);

        routineRepository.save(routine);

        return routineAvgScore;
    }

    public List<RoutineScoreDto> getTop5RoutineList(){
        List<Routine> top5ByRoutineAvgScore = routineRepository.findTop5ByOrderByRoutineAvgScoreDesc();
        List<RoutineScoreDto> dtoList=new ArrayList<>();

        if(top5ByRoutineAvgScore.size()<5) {
            top5ByRoutineAvgScore = top5ByRoutineAvgScore.subList(0, top5ByRoutineAvgScore.size());
        }

        for(Routine routine : top5ByRoutineAvgScore){
            RoutineScoreDto dto = RoutineScoreDto.builder()
                    .routineId(routine.getRoutineId())
                    .userName(routine.getUserName())
                    .routineName(routine.getRoutineName())
                    .routineAvgScore(routine.getRoutineAvgScore())
                    .evaluateCnt(routine.getEvaluateCnt())
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<RoutineScoreDto> getMyRoutines(String userName){
        List<Routine> myRoutineList = routineRepository.findByUserName(userName);
        List<RoutineScoreDto> result=new ArrayList<>();

        for(Routine routine : myRoutineList){
            RoutineScoreDto dto = RoutineScoreDto.builder()
                    .routineName(routine.getRoutineName())
                    .routineId(routine.getRoutineId())
                    .routineAvgScore(routine.getRoutineAvgScore())
                    .evaluateCnt(routine.getEvaluateCnt())
                    .build();

            result.add(dto);
        }

        return result;
    }

    private Object[] arrayParse(String notParsedString){

        notParsedString=notParsedString.replace("[", "");
        notParsedString=notParsedString.replace("]", "");
        notParsedString=notParsedString.replace(" ", "");

        Object[] parsedString = notParsedString.split(",");
        return parsedString;
    }
}

