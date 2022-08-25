package hometoogether.hometoogether.domain.post.service;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.repository.PoseRepository;
import hometoogether.hometoogether.domain.post.domain.Challenge;
import hometoogether.hometoogether.domain.post.dto.challenge.ReadChallengeRes;
import hometoogether.hometoogether.domain.post.dto.challenge.CreateChallengeReq;
import hometoogether.hometoogether.domain.post.dto.challenge.SimpleChallengeRes;
import hometoogether.hometoogether.domain.post.dto.challenge.UpdateChallengeReq;
import hometoogether.hometoogether.domain.post.repository.ChallengeRepository;
import hometoogether.hometoogether.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class ChallengeService {

    private final PoseRepository poseRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public Long createChallenge(User user, CreateChallengeReq createChallengeReq)  {
        Pose pose = poseRepository.findPoseById(createChallengeReq.getPoseId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 Pose 입니다."));

        if (!pose.getUser().equals(user)) {
            throw new RuntimeException("자신의 Pose에 대한 챌린지만 생성할 수 있습니다.");
        }

        Challenge challenge = Challenge.builder()
                .title(createChallengeReq.getTitle())
                .content(createChallengeReq.getContent())
                .pose(pose)
                .build();

        return challengeRepository.save(challenge).getId();
    }

    public ReadChallengeRes readChallenge(Long postId) {
        Challenge challenge = challengeRepository.findChallengeById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 챌린지입니다."));
        return new ReadChallengeRes(challenge);
    }

    public List<SimpleChallengeRes> getChallengeList(Pageable pageable) {
        return challengeRepository.findAll().stream()
                .map(c -> new SimpleChallengeRes(c))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updateChallenge(User user, Long postId, UpdateChallengeReq updateChallengeReq) {
        Challenge challenge = challengeRepository.findChallengeById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 챌린지입니다."));

        if (!challenge.getPose().getUser().equals(user)) {
            throw new RuntimeException("챌린지를 수정할 권한이 없습니다.");
        }

        Pose pose = poseRepository.findById(updateChallengeReq.getPoseId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 Pose 입니다."));

        if (!pose.getUser().equals(user)) {
            throw new RuntimeException("자신의 Pose에 대한 챌린지만 생성할 수 있습니다.");
        }

        challenge.update(updateChallengeReq.getTitle(), updateChallengeReq.getContent(), pose);
        return challenge.getId();
    }

    @Transactional
    public Long deleteChallenge(User user, Long postId) {
        Challenge challenge = challengeRepository.findChallengeById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 챌린지입니다."));

        if (!challenge.getPose().getUser().equals(user)) {
            throw new RuntimeException("챌린지를 삭제할 권한이 없습니다.");
        }

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
