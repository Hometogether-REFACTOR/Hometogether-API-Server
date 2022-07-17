package hometoogether.hometoogether.domain.trial.service;

import hometoogether.hometoogether.domain.challenge.domain.Challenge;
import hometoogether.hometoogether.domain.challenge.repository.ChallengeRepository;
import hometoogether.hometoogether.domain.pose.domain.Keypoints;
import hometoogether.hometoogether.domain.pose.domain.TrialPose;
import hometoogether.hometoogether.domain.pose.repository.TrialPoseRepository;
import hometoogether.hometoogether.domain.pose.service.PoseService;
import hometoogether.hometoogether.domain.trial.domain.Trial;
import hometoogether.hometoogether.domain.trial.dto.Challenge_vs_TrialDto;
import hometoogether.hometoogether.domain.trial.dto.TrialRequestDto;
import hometoogether.hometoogether.domain.trial.dto.TrialResponseDto;
import hometoogether.hometoogether.domain.trial.repository.TrialRepository;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrialService {

    private final UserRepository userRepository;
    private final TrialRepository trialRepository;
    private final ChallengeRepository challengeRepository;
    private final TrialPoseRepository trialPoseRepository;
    private final PoseService poseService;

    @Transactional
    public Long save(Long challengeId, TrialRequestDto trialRequestDto) throws JCodecException, IOException, ParseException {
        Challenge challenge = challengeRepository.getById(challengeId);
        if ("photo".equals(challenge.getType())){
            return saveTrialPhoto(challengeId, trialRequestDto);
        }
        return saveTrialVideo(challengeId, trialRequestDto);
    }

    @Transactional
    public Long saveTrialPhoto(Long challengeId, TrialRequestDto trialRequestDto) throws IOException, ParseException, JCodecException {
        // parameter로 SessionUser 받아오게 구현 예정

        //TrialPose 생성
        //url, poseInfoList, user
        MultipartFile multipartFile = trialRequestDto.getFile();
        String url = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
        File file = new File(url);
        multipartFile.transferTo(file);

//        String thumbnail_url = createThumbnail(file);

        User user = userRepository.findUserByUserName(trialRequestDto.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. username=" + trialRequestDto.getUserName()));

        TrialPose trialPose = TrialPose.builder()
                .url(url)
//                .thumbnail_url(thumbnail_url)
                .user(user)
                .build();
        trialPoseRepository.save(trialPose);

        poseService.estimatePosePhoto(trialPose.getId(), url, "trial");
//        poseService.estimatePoseVideo(trialPose.getId(), url, "trial");

        //User <-> ChallengePose 매핑
        user.addTrialPose(trialPose);

        //Trial 생성
        Trial trial = Trial.builder()
                .trialPose(trialPose)
                .build();

        //trialPose <-> trial 상호 매핑
        trialPose.setTrial(trial);

        //Trial <-> Challenge 상호 매핑
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + challengeId));
        challenge.addTrial(trial);

        trial.setType(challenge.getType());
        trial.setChallenge(challenge);

        return trialRepository.save(trial).getId();
    }

    @Transactional
    public Long saveTrialVideo(Long challengeId, TrialRequestDto trialRequestDto) throws IOException, ParseException, JCodecException {
        // parameter로 SessionUser 받아오게 구현 예정

        //TrialPose 생성
        //url, poseInfoList, user
        MultipartFile multipartFile = trialRequestDto.getFile();
        String url = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
        File file = new File(url);
        multipartFile.transferTo(file);

//        String thumbnail_url = createThumbnail(file);

        User user = userRepository.findUserByUserName(trialRequestDto.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. username=" + trialRequestDto.getUserName()));

        TrialPose trialPose = TrialPose.builder()
                .url(url)
//                .thumbnail_url(thumbnail_url)
                .user(user)
                .build();
        trialPoseRepository.save(trialPose);

        poseService.estimatePoseVideo(trialPose.getId(), url, "trial");

        //User <-> ChallengePose 매핑
        user.addTrialPose(trialPose);

        //Trial 생성
        Trial trial = Trial.builder()
                .trialPose(trialPose)
                .build();

        //trialPose <-> trial 상호 매핑
        trialPose.setTrial(trial);

        //Trial <-> Challenge 상호 매핑
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + challengeId));
        challenge.addTrial(trial);

        trial.setType(challenge.getType());
        trial.setChallenge(challenge);

        return trialRepository.save(trial).getId();
    }

    @Transactional
    public String createThumbnail(File source) throws IOException, JCodecException {
        String url = UUID.randomUUID().toString();
        Picture picture = FrameGrab.getFrameFromFile(source, 0);
        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
        ImageIO.write(bufferedImage, "jpg", new File(url));
        return url;
    }

    @Transactional
    public Challenge_vs_TrialDto compareDetail(Long trialId){
        Trial trial = trialRepository.findById(trialId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + trialId));
        return new Challenge_vs_TrialDto(trial);
    }

    @Transactional
    public double runSimilarity(Long trialId){

        Trial trial = trialRepository.findById(trialId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + trialId));

        Challenge challenge = trial.getChallenge();

        List<Keypoints> keypointsListA = challenge.getChallengePose().getKeypointsList();
        List<Keypoints> keypointsListB = trial.getTrialPose().getKeypointsList();

        double similarity = 0.0;

        System.out.println("photo.equals(challenge.getType()) = " + "photo".equals(challenge.getType()));

        if ("photo".equals(challenge.getType())){
            System.out.println("hihi1");
            List<Double> keypointsA = keypointsListA.get(0).getKeypoints();
            List<Double> keypointsB = keypointsListB.get(0).getKeypoints();
            similarity = poseService.estimateSimilarity(keypointsA , keypointsB);
            System.out.println("similarity for photo = " + similarity);
        }
        else {
            System.out.println("hihi2");
            System.out.println("challengeId = " + challenge.getId());
            System.out.println("trialId = " + trialId);
            similarity = poseService.DTWDistance(keypointsListA, keypointsListB);
            System.out.println("similarity for video = " + similarity);
        }
        System.out.println("similarity result = " + similarity);
        similarity = similarity * 100;
        similarity = Math.round(similarity * 100) / 100.0;
        trial.setScore(similarity);
        trialRepository.save(trial);

        return similarity;
    }

    @Transactional
    public List<TrialResponseDto> getBestTrials(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + 0));
        List<Trial> trials = challenge.getTrialList();
        MyComparator comparator = new MyComparator();
        Collections.sort(trials, comparator);
        List<Trial> best_trials;
        if (trials.size() < 5){
            best_trials = trials.subList(0, trials.size());
        } else{
            best_trials = trials.subList(0, 5);
        }
        return best_trials.stream().map(TrialResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<TrialResponseDto> getMyList(String username) {
        User user = userRepository.findUserByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. username=" + username));

        List<Trial> trials = new ArrayList<>();

        for (TrialPose trialPose : user.getTrialPoseList()) {
            trials.add(trialPose.getTrial());
        }

        return trials.stream().map(TrialResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public TrialResponseDto getTrial(Long trialId) {
        Trial trial = trialRepository.findById(trialId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + trialId));
        return new TrialResponseDto(trial);
    }

    @Transactional
    public List<TrialResponseDto> getTrialList(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + 0));
        List<Trial> trials = challenge.getTrialList();
        return trials.stream().map(TrialResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long deleteTrial(Long trialId) {
        Trial trial = trialRepository.findById(trialId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + trialId));
        trialRepository.delete(trial);
        return trialId;
    }
}