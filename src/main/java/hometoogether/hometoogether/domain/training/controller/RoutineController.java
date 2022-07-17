package hometoogether.hometoogether.domain.training.controller;

import hometoogether.hometoogether.domain.training.domain.*;
import hometoogether.hometoogether.domain.training.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class RoutineController {

    private final RoutineService routineService;

    @GetMapping("/routines")
    public List<RoutineDto> getRoutineList() {
        return routineService.getRoutines();
    }

    @GetMapping("/routine/{routineId}")
    public List<TrainingVO> getTrainings(@PathVariable Long routineId) {
        return routineService.getTrainingList(routineId);
    }

    @PostMapping("/routine")
    public void saveRoutine(@RequestBody Map<String, Object> routine) {
        routineService.saveRoutine(routine);
    }

    @PutMapping("/routine/{routineId}")
    public float editRoutineAvgScore(@PathVariable Long routineId, @RequestBody Map<String, String> evaluation){
        return routineService.editRoutineAvgScore(routineId, evaluation);
    }

    @GetMapping("/routine/top5")
    public List<RoutineScoreDto> getTop5RoutineList(){
        return routineService.getTop5RoutineList();
    }

    @GetMapping("/myRoutines/{userName}")
    public List<RoutineScoreDto> getMyRoutines(@PathVariable String userName){
        return routineService.getMyRoutines(userName);
    }
}

