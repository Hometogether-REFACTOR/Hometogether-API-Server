package hometoogether.hometoogether.domain.post.controller;

import hometoogether.hometoogether.domain.post.dto.challenge.ReadChallengeRes;
import hometoogether.hometoogether.domain.post.dto.challenge.CreateChallengeReq;
import hometoogether.hometoogether.domain.post.dto.challenge.PreviewChallengeRes;
import hometoogether.hometoogether.domain.post.dto.challenge.UpdateChallengeReq;
import hometoogether.hometoogether.domain.post.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<Long> createChallenge(CreateChallengeReq createChallengeReq) throws IOException {
        return ResponseEntity.ok(challengeService.createChallenge(createChallengeReq));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ReadChallengeRes> readChallenge(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(challengeService.readChallenge(postId));
    }

    @GetMapping
    public ResponseEntity<List<PreviewChallengeRes>> getChallengeList(Pageable pageable){
        return ResponseEntity.ok(challengeService.getChallengeList(pageable));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Long> updateChallenge(@PathVariable("postId") Long postId, UpdateChallengeReq updateChallengeReq){
        return ResponseEntity.ok(challengeService.updateChallenge(postId, updateChallengeReq));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Long> deleteChallenge(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(challengeService.deleteChallenge(postId));
    }

//    @GetMapping("/trending")
//    public List<PreviewChallengeRes> getTrendingList(){
//        return challengeService.getTrendingChallenges();
//    }
//
//    @GetMapping("/my/{username}")
//    public List<PreviewChallengeRes> getMyList(@PathVariable("username") String username){
//        return challengeService.getMyList(username);
//    }
//
//    @GetMapping("/my/count/{username}")
//    public int getMyCount(@PathVariable("username") String username){
//        return challengeService.getMyList(username).size();
//    }
}