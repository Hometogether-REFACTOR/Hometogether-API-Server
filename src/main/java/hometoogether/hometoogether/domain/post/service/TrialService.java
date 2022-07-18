package hometoogether.hometoogether.domain.post.service;

import hometoogether.hometoogether.domain.pose.Pose;
import hometoogether.hometoogether.domain.post.domain.Post;
import hometoogether.hometoogether.domain.post.dto.trial.ReadTrialRes;
import hometoogether.hometoogether.domain.post.dto.trial.UpdateTrialReq;
import hometoogether.hometoogether.domain.pose.service.PoseService;
import hometoogether.hometoogether.domain.post.dto.trial.CreateTrialReq;
import hometoogether.hometoogether.domain.post.dto.trial.PreviewTrialRes;
import hometoogether.hometoogether.domain.post.repository.PostRepository;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrialService {

    private final FileService fileService;
    private final PoseService poseService;

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long createTrial(CreateTrialReq createTrialReq) throws IOException {
        User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException(""));
        Post challenge = postRepository.findById(createTrialReq.getChallengeId()).orElseThrow(() -> new RuntimeException(""));

        String url = fileService.createFileURL(createTrialReq.getFile());
        // TODO: 썸네일 생성 후 URL 반환, Pose에 저장

        Pose pose = Pose.builder()
                .poseType(challenge.getPose().getPoseType())
                .originalUrl(url)
                .build();

        Post post = Post.builder()
                .user(user)
                .pose(pose)
                .title(createTrialReq.getTitle())
                .content(createTrialReq.getContent())
                .build();

        user.addPost(post);

        // TODO: 비동기 처리로 자세 분석 완료되면 유저에게 알림(SSE)
//        poseService.estimatePose(pose);

        return postRepository.save(post).getId();
    }

    @Transactional(readOnly=true)
    public ReadTrialRes readTrial(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(""));
        return null;
    }

    @Transactional(readOnly=true)
    public List<PreviewTrialRes> getTrialList(Long challengeId, Pageable pageable) {
        return null;
    }

    @Transactional
    public Long updateTrial(Long postId, UpdateTrialReq updateTrialReq) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(""));
        return null;
    }

    @Transactional
    public Long deleteTrial(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(""));
        postRepository.delete(post);
        return post.getId();
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