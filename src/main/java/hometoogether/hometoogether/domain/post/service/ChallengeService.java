package hometoogether.hometoogether.domain.post.service;

import hometoogether.hometoogether.domain.pose.Pose;
import hometoogether.hometoogether.domain.post.domain.Post;
import hometoogether.hometoogether.domain.post.dto.challenge.ReadChallengeRes;
import hometoogether.hometoogether.domain.post.dto.challenge.CreateChallengeReq;
import hometoogether.hometoogether.domain.post.dto.challenge.PreviewChallengeRes;
import hometoogether.hometoogether.domain.post.dto.challenge.UpdateChallengeReq;
import hometoogether.hometoogether.domain.pose.service.PoseService;
import hometoogether.hometoogether.domain.post.repository.PostRepository;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChallengeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PoseService poseService;

    @Value("${spring.servlet.multipart.location}")
    String videoPath;

    @Transactional
    public Long createChallenge(CreateChallengeReq createChallengeReq) throws IOException {
        User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException(""));

        String url = createFileURL(createChallengeReq.getFile());
        // TODO: 썸네일 생성 후 URL 반환, Pose에 저장

        Pose pose = Pose.builder()
                .poseType(createChallengeReq.getPoseType())
                .originalUrl(url)
                .build();

        Post post = Post.builder()
                .user(user)
                .pose(pose)
                .title(createChallengeReq.getTitle())
                .content(createChallengeReq.getContent())
                .build();

        user.addPost(post);

        // TODO: 비동기 처리로 자세 분석 완료되면 유저에게 알림(SSE)
//        poseService.estimatePose(pose);

        return postRepository.save(post).getId();
    }

    private String createFileURL(MultipartFile multipartFile) throws IOException {
        String url = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        File file = new File(url);
        multipartFile.transferTo(file);
        return url;
    }

    private String createThumbnail(File source) throws IOException, JCodecException {
        String url = UUID.randomUUID().toString();
        Picture picture = FrameGrab.getFrameFromFile(source, 0);
        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
        ImageIO.write(bufferedImage, "jpg", new File(url));
        return url;
    }

    @Transactional(readOnly=true)
    public ReadChallengeRes readChallenge(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(""));
        return null;
    }

    @Transactional(readOnly=true)
    public List<PreviewChallengeRes> getChallengeList(Pageable pageable) {
        return null;
    }

    @Transactional
    public Long updateChallenge(Long postId, UpdateChallengeReq updateChallengeReq) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(""));
        return null;
    }

    @Transactional
    public Long deleteChallenge(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(""));
        postRepository.delete(post);
        return post.getId();
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
