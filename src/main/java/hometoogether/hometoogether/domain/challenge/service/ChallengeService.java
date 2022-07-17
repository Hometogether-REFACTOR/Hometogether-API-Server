package hometoogether.hometoogether.domain.challenge.service;

import hometoogether.hometoogether.domain.challenge.dto.ChallengeDetailResponseDto;
import hometoogether.hometoogether.domain.challenge.dto.ChallengeRequestDto;
import hometoogether.hometoogether.domain.challenge.dto.ChallengeResponseDto;
import hometoogether.hometoogether.domain.challenge.domain.Challenge;
import hometoogether.hometoogether.domain.challenge.repository.ChallengeRepository;
import hometoogether.hometoogether.domain.pose.domain.ChallengePose;
import hometoogether.hometoogether.domain.pose.repository.ChallengePoseRepository;
import hometoogether.hometoogether.domain.pose.service.PoseService;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChallengeService {

    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengePoseRepository challengePoseRepository;
    private final PoseService poseService;

    @Value("${spring.servlet.multipart.location}")
    String videoPath;

    @Transactional
    public Long saveChallengePhoto(ChallengeRequestDto challengeRequestDto) throws IOException, ParseException {
        // parameter로 SessionUser 받아오게 구현 예정

        //ChallengePose 생성
        //url, poseInfoList, user
        MultipartFile multipartFile = challengeRequestDto.getFile();
        String url = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
        File file = new File(url);
        multipartFile.transferTo(file);

        User user = userRepository.findUserByUserName(challengeRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. username=" + challengeRequestDto.getUsername()));

        ChallengePose challengePose = ChallengePose.builder()
                .url(url)
                .user(user)
                .build();

        challengePoseRepository.save(challengePose);

        poseService.estimatePosePhoto(challengePose.getId(), url, "challenge");
//        List<PoseDetail> poseDetailList = poseService.estimatePosePhoto(url);
//        List<PoseInfo> poseInfoList = new ArrayList<>();
//        for (PoseDetail pd : poseDetailList){
//            PoseInfo poseInfo = PoseInfo.builder()
//                    .poseDetail(pd)
//                    .build();
//            poseInfoList.add(poseInfo);
//        }

        //User <-> ChallengePose 매핑
        user.addChallengePose(challengePose);

        //Challenge 생성
        Challenge challenge = Challenge.builder()
                .type("photo")
                .challengePose(challengePose)
                .title(challengeRequestDto.getTitle())
                .context(challengeRequestDto.getContext())
                .build();

        //challengePose <-> challenge 매핑
        challengePose.setChallenge(challenge);

        return challengeRepository.save(challenge).getId();
    }

    @Transactional
    public Long saveChallengeVideo(ChallengeRequestDto challengeRequestDto) throws IOException, ParseException, JCodecException, InterruptedException {
        // parameter로 SessionUser 받아오게 구현 예정

        //ChallengePose 생성
        //url, poseInfoList, user
        MultipartFile multipartFile = challengeRequestDto.getFile();
        String url = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
        File file = new File(url);
        multipartFile.transferTo(file);


//        Thread.sleep(5000);
//        String thumbnail_url = UUID.randomUUID().toString();
//        Picture picture = FrameGrab.getFrameFromFile(file, 15);
//        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
//        ImageIO.write(bufferedImage, "jpg", new File(url));

        User user = userRepository.findUserByUserName(challengeRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. username=" + challengeRequestDto.getUsername()));

        ChallengePose challengePose = ChallengePose.builder()
                .url(url)
//                .thumbnail_url(thumbnail_url)
                .user(user)
                .build();
        challengePoseRepository.save(challengePose);

        poseService.estimatePoseVideo(challengePose.getId(), url, "challenge");

        //User <-> ChallengePose 매핑
        user.addChallengePose(challengePose);

        //Challenge 생성
        Challenge challenge = Challenge.builder()
                .type("video")
                .challengePose(challengePose)
                .title(challengeRequestDto.getTitle())
                .context(challengeRequestDto.getContext())
                .build();

        //challengePose <-> challenge 매핑
        challengePose.setChallenge(challenge);

        return challengeRepository.save(challenge).getId();
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
    public ChallengeDetailResponseDto getChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + challengeId));
        return new ChallengeDetailResponseDto(challenge);
    }

    @Transactional
    public List<ChallengeResponseDto> getMyList(String username) {
        User user = userRepository.findUserByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. username=" + username));

        List<Challenge> challenges = new ArrayList<>();

        for (ChallengePose challengePose : user.getChallengePoseList()) {
            challenges.add(challengePose.getChallenge());
        }

        return challenges.stream().map(ChallengeResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<ChallengeResponseDto> getTrendingChallenges() {
        List<Challenge> challenges = challengeRepository.findTop5ByOrderByTrialCountDesc();
        return challenges.stream().map(ChallengeResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<ChallengeResponseDto> getChallengeList(Pageable pageable) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "create_date");
//        List<Challenge> challenges = challengeRepository.findAll();
//        return challenges.stream().map(ChallengeResponseDto::new).collect(Collectors.toList());
        return challengeRepository.findAll(pageable).map(ChallengeResponseDto::new).getContent();
    }

    @Transactional
    public Long updateChallenge(Long challengeId, ChallengeRequestDto param) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + challengeId));
        ChallengePose challengePose = challenge.getChallengePose();
//        challengePose.setUrl(param.getUrl());
        challenge.update(challengePose, param.getTitle(), param.getContext());
        return challengeId;
    }

    @Transactional
    public Long deleteChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + challengeId));
        challengeRepository.delete(challenge);
        return challengeId;
    }
}
