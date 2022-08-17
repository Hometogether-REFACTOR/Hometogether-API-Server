package hometoogether.hometoogether.domain.post.service;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.service.FileService;
import hometoogether.hometoogether.domain.post.domain.Challenge;
import hometoogether.hometoogether.domain.post.dto.challenge.ReadChallengeRes;
import hometoogether.hometoogether.domain.post.dto.challenge.CreateChallengeReq;
import hometoogether.hometoogether.domain.post.dto.challenge.PreviewChallengeRes;
import hometoogether.hometoogether.domain.post.dto.challenge.UpdateChallengeReq;
import hometoogether.hometoogether.domain.pose.service.PoseService;
import hometoogether.hometoogether.domain.post.repository.ChallengeRepository;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChallengeService {

    private final FileService fileService;
    private final PoseService poseService;

    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public Long createChallenge(CreateChallengeReq createChallengeReq) throws IOException {
        User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException(""));

        Challenge challenge = Challenge.builder()
                .title(createChallengeReq.getTitle())
                .content(createChallengeReq.getContent())
                .build();

        return challengeRepository.save(challenge).getId();
    }

    @Transactional(readOnly=true)
    public ReadChallengeRes readChallenge(Long postId) {
        Challenge challenge = challengeRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(""));
        return null;
    }

    @Transactional(readOnly=true)
    public List<PreviewChallengeRes> getChallengeList(Pageable pageable) {
        return null;
    }

    @Transactional
    public Long updateChallenge(Long postId, UpdateChallengeReq updateChallengeReq) {
        Challenge challenge = challengeRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(""));
        return null;
    }

    @Transactional
    public Long deleteChallenge(Long postId) {
        Challenge challenge = challengeRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(""));
        challengeRepository.delete(challenge);
        return challenge.getId();
    }

//    @Transactional
//    public List<PreviewChallengeRes> getMyList(String username) {
////        User user = userRepository.findUserByUserName(username)
////                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. username=" + username));
//
//        List<Challenge> challenges = new ArrayList<>();
//
////        for (ChallengePose challengePose : user.getChallengePoseList()) {
////            challenges.add(challengePose.getChallenge());
////        }
//
//        return challenges.stream().map(PreviewChallengeRes::new).collect(Collectors.toList());
//    }

//    @Transactional
//    public List<PreviewChallengeRes> getTrendingChallenges() {
//        List<Challenge> challenges = challengeRepository.findTop5ByOrderByTrialCountDesc();
//        return challenges.stream().map(PreviewChallengeRes::new).collect(Collectors.toList());
//    }
}
