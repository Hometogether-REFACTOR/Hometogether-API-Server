package hometoogether.hometoogether.domain.pose.controller;

import hometoogether.hometoogether.domain.challenge.domain.Challenge;
import hometoogether.hometoogether.domain.challenge.dto.ChallengeRequestDto;
import hometoogether.hometoogether.domain.challenge.repository.ChallengeRepository;
import hometoogether.hometoogether.domain.challenge.service.ChallengeService;
import hometoogether.hometoogether.domain.pose.domain.Keypoints;
import hometoogether.hometoogether.domain.pose.service.PoseService;
import hometoogether.hometoogether.domain.trial.domain.Trial;
import hometoogether.hometoogether.domain.trial.repository.TrialRepository;
import hometoogether.hometoogether.domain.trial.service.TrialService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class PoseController {

    private final PoseService poseService;
    private final ChallengeRepository challengeRepository;
    private final TrialRepository trialRepository;

    @GetMapping("/demo/similarity")
    @Transactional
    public double similarity(){
        Challenge challenge = challengeRepository.findById(14L)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. challenge_id=" + 1));

        Trial trial = trialRepository.findById(9L)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. trial_id=" + 1));

        List<Keypoints> keypointsList1 = challenge.getChallengePose().getKeypointsList();
        List<Keypoints> keypointsList2 = trial.getTrialPose().getKeypointsList();

        double similarity = poseService.DTWDistance(keypointsList1, keypointsList2);

        return similarity;
    }
}
