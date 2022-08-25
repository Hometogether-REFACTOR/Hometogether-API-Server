package hometoogether.hometoogether.domain.post.service;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import hometoogether.hometoogether.domain.pose.repository.PoseRepository;
import hometoogether.hometoogether.domain.post.domain.Challenge;
import hometoogether.hometoogether.domain.post.domain.Trial;
import hometoogether.hometoogether.domain.post.dto.trial.ReadTrialRes;
import hometoogether.hometoogether.domain.post.dto.trial.UpdateTrialReq;
import hometoogether.hometoogether.domain.post.dto.trial.CreateTrialReq;
import hometoogether.hometoogether.domain.post.dto.trial.SimpleTrialRes;
import hometoogether.hometoogether.domain.post.repository.ChallengeRepository;
import hometoogether.hometoogether.domain.post.repository.TrialRepository;
import hometoogether.hometoogether.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrialService {

    private final PoseRepository poseRepository;
    private final ChallengeRepository challengeRepository;
    private final TrialRepository trialRepository;

    @Transactional
    public Long createTrial(User user, CreateTrialReq createTrialReq)  {
        Challenge challenge = challengeRepository.findById(createTrialReq.getChallengeId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 챌린지입니다."));

        Pose pose = poseRepository.findById(createTrialReq.getPoseId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 Pose 입니다."));

        if (!pose.getUser().equals(user)) {
            throw new RuntimeException("자신의 Pose에 대한 챌린지만 생성할 수 있습니다.");
        }

        // 자세 유사도 계산
        // Double score = 0;

        Trial trial = Trial.builder()
                .title(createTrialReq.getTitle())
                .content(createTrialReq.getContent())
                .pose(pose)
                .challenge(challenge)
                .build();

        return trialRepository.save(trial).getId();
    }

    @Transactional(readOnly=true)
    public ReadTrialRes readTrial(Long postId) {
        Trial trial = trialRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 Trial입니다."));
        return new ReadTrialRes(trial);
    }

    @Transactional(readOnly=true)
    public List<SimpleTrialRes> getTrialList(Long challengeId, Pageable pageable) {
//        trialRepository.findByChallengeId()
        return null;
    }

    @Transactional
    public Long updateTrial(User user, Long postId, UpdateTrialReq updateTrialReq) {
        Trial trial = trialRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 Trial입니다."));

        if (!trial.getPose().getUser().equals(user)) {
            throw new RuntimeException("Trial을 수정할 권한이 없습니다.");
        }

        Pose pose = poseRepository.findById(updateTrialReq.getPoseId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 Pose 입니다."));

        if (!pose.getUser().equals(user)) {
            throw new RuntimeException("자신의 Pose에 대한 Trial만 생성할 수 있습니다.");
        }

        trial.update(updateTrialReq.getTitle(), updateTrialReq.getContent(), pose);
        return trial.getId();
    }

    @Transactional
    public Long deleteTrial(User user, Long postId) {
        Trial trial = trialRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 Trial입니다."));

        if (!trial.getPose().getUser().equals(user)) {
            throw new RuntimeException("Trial을 삭제할 권한이 없습니다.");
        }

        trialRepository.delete(trial);
        return trial.getId();
    }

//    @Transactional
//    public Challenge_vs_TrialDto compareDetail(Long trialId){
//        Trial trial = trialRepository.findById(trialId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + trialId));
//        return new Challenge_vs_TrialDto(trial);
//    }
//
//    @Transactional
//    public double runSimilarity(Long trialId){
//
//        Trial trial = trialRepository.findById(trialId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + trialId));
//
//        Challenge challenge = trial.getChallenge();
//
//        List<Keypoints> keypointsListA = challenge.getChallengePose().getKeypointsList();
//        List<Keypoints> keypointsListB = trial.getTrialPose().getKeypointsList();
//
//        double similarity = 0.0;
//
//        System.out.println("photo.equals(challenge.getType()) = " + "photo".equals(challenge.getType()));
//
//        if ("photo".equals(challenge.getType())){
//            System.out.println("hihi1");
//            List<Double> keypointsA = keypointsListA.get(0).getKeypoints();
//            List<Double> keypointsB = keypointsListB.get(0).getKeypoints();
//            similarity = poseService.estimateSimilarity(keypointsA , keypointsB);
//            System.out.println("similarity for photo = " + similarity);
//        }
//        else {
//            System.out.println("hihi2");
//            System.out.println("challengeId = " + challenge.getId());
//            System.out.println("trialId = " + trialId);
//            similarity = poseService.DTWDistance(keypointsListA, keypointsListB);
//            System.out.println("similarity for video = " + similarity);
//        }
//        System.out.println("similarity result = " + similarity);
//        similarity = similarity * 100;
//        similarity = Math.round(similarity * 100) / 100.0;
//        trial.setScore(similarity);
//        trialRepository.save(trial);
//
//        return similarity;
//    }
//
//    @Transactional
//    public List<PreviewTrialRes> getBestTrials(Long challengeId) {
//        Challenge challenge = challengeRepository.findById(challengeId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + 0));
//        List<Trial> trials = challenge.getTrialList();
//        MyComparator comparator = new MyComparator();
//        Collections.sort(trials, comparator);
//        List<Trial> best_trials;
//        if (trials.size() < 5){
//            best_trials = trials.subList(0, trials.size());
//        } else{
//            best_trials = trials.subList(0, 5);
//        }
//        return best_trials.stream().map(PreviewTrialRes::new).collect(Collectors.toList());
//    }
//
//    @Transactional
//    public List<PreviewTrialRes> getMyList(String username) {
////        User user = userRepository.findUserByUserName(username)
////                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. username=" + username));
//
//        List<Trial> trials = new ArrayList<>();
//
////        for (TrialPose trialPose : user.getTrialPoseList()) {
////            trials.add(trialPose.getTrial());
////        }
//
//        return trials.stream().map(PreviewTrialRes::new).collect(Collectors.toList());
//    }
}