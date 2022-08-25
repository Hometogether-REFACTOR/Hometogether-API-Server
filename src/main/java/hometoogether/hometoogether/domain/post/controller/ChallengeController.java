package hometoogether.hometoogether.domain.post.controller;

import hometoogether.hometoogether.domain.post.dto.challenge.ReadChallengeRes;
import hometoogether.hometoogether.domain.post.dto.challenge.CreateChallengeReq;
import hometoogether.hometoogether.domain.post.dto.challenge.SimpleChallengeRes;
import hometoogether.hometoogether.domain.post.dto.challenge.UpdateChallengeReq;
import hometoogether.hometoogether.domain.post.service.ChallengeService;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenges")
public class ChallengeController {

    private final JwtUtil jwtUtil;
    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<Long> createChallenge(CreateChallengeReq createChallengeReq) {
        User user = jwtUtil.getUserFromHeader();
        return ResponseEntity.ok(challengeService.createChallenge(user, createChallengeReq));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ReadChallengeRes> readChallenge(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(challengeService.readChallenge(postId));
    }

    @GetMapping
    public ResponseEntity<List<SimpleChallengeRes>> getChallengeList(Pageable pageable){
        return ResponseEntity.ok(challengeService.getChallengeList(pageable));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Long> updateChallenge(@PathVariable("postId") Long postId, UpdateChallengeReq updateChallengeReq){
        User user = jwtUtil.getUserFromHeader();
        return ResponseEntity.ok(challengeService.updateChallenge(user, postId, updateChallengeReq));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Long> deleteChallenge(@PathVariable("postId") Long postId){
        User user = jwtUtil.getUserFromHeader();
        return ResponseEntity.ok(challengeService.deleteChallenge(user, postId));
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