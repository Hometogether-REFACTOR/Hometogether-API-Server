package hometoogether.hometoogether.domain.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hometoogether.hometoogether.domain.post.dto.trial.CreateTrialReq;
import hometoogether.hometoogether.domain.post.dto.trial.ReadTrialRes;
import hometoogether.hometoogether.domain.post.dto.trial.SimpleTrialRes;
import hometoogether.hometoogether.domain.post.dto.trial.UpdateTrialReq;
import hometoogether.hometoogether.domain.post.service.TrialService;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trials")
public class TrialController {

    private final JwtUtil jwtUtil;
    private final TrialService trialService;

    @PostMapping
    public ResponseEntity<Long> createTrial(CreateTrialReq createTrialReq) throws JsonProcessingException {
        User user = jwtUtil.getUserFromHeader();
        return ResponseEntity.ok(trialService.createTrial(user, createTrialReq));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ReadTrialRes> readTrial(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(trialService.readTrial(postId));
    }

    @GetMapping("/list/{challengeId}")
    public List<SimpleTrialRes> getTrialList(@PathVariable("challengeId") Long challengeId, Pageable pageable){
        return trialService.getTrialList(challengeId, pageable);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Long> updateTrial(@PathVariable("postId") Long postId, UpdateTrialReq updateTrialReq){
        User user = jwtUtil.getUserFromHeader();
        return ResponseEntity.ok(trialService.updateTrial(user, postId, updateTrialReq));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Long> deleteTrial(@PathVariable("postId") Long postId){
        User user = jwtUtil.getUserFromHeader();
        return ResponseEntity.ok(trialService.deleteTrial(user, postId));
    }

//    @GetMapping("/trials/{trialId}/estimate/detail")
//    public Challenge_vs_TrialDto compare(@PathVariable("trialId") Long trialId){
//        return trialService.compareDetail(trialId);
//    }
//
//    @GetMapping("/trials/{trialId}/estimate")
//    public double estimate(@PathVariable("trialId") Long trialId){
//        return trialService.runSimilarity(trialId);
//    }
//
//    @GetMapping("/challenges/{challengeId}/best_trials")
//    public List<TrialResponseDto> getBestTrialList(@PathVariable("challengeId") Long challengeId){
//        return trialService.getBestTrials(challengeId);
//    }
//
//    @GetMapping("/trials/my/{username}")
//    public List<TrialResponseDto> getMyList(@PathVariable("username") String username){
//        return trialService.getMyList(username);
//    }
}