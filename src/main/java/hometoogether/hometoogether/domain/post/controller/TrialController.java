package hometoogether.hometoogether.domain.post.controller;

import hometoogether.hometoogether.domain.post.dto.trial.CreateTrialReq;
import hometoogether.hometoogether.domain.post.dto.trial.ReadTrialRes;
import hometoogether.hometoogether.domain.post.dto.trial.PreviewTrialRes;
import hometoogether.hometoogether.domain.post.dto.trial.UpdateTrialReq;
import hometoogether.hometoogether.domain.post.service.TrialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trials")
public class TrialController {

    private final TrialService trialService;

    @PostMapping
    public ResponseEntity<Long> createTrial(CreateTrialReq createTrialReq) throws IOException {
        return ResponseEntity.ok(trialService.createTrial(createTrialReq));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ReadTrialRes> readTrial(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(trialService.readTrial(postId));
    }

    @GetMapping("/of/{challengeId}/trials")
    public List<PreviewTrialRes> getTrialList(@PathVariable("challengeId") Long challengeId, Pageable pageable){
        return trialService.getTrialList(challengeId, pageable);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Long> updateTrial(@PathVariable("postId") Long postId, UpdateTrialReq updateTrialReq){
        return ResponseEntity.ok(trialService.updateTrial(postId, updateTrialReq));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Long> deleteTrial(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(trialService.deleteTrial(postId));
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