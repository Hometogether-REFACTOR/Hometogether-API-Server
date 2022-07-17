package hometoogether.hometoogether.domain.trial.controller;

import hometoogether.hometoogether.domain.trial.dto.Challenge_vs_TrialDto;
import hometoogether.hometoogether.domain.trial.dto.TrialRequestDto;
import hometoogether.hometoogether.domain.trial.dto.TrialResponseDto;
import hometoogether.hometoogether.domain.trial.service.TrialService;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.JCodecException;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api")
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class TrialController {

    private final TrialService trialService;

    @Transactional
    @PostMapping("/challenges/{challengeId}/trials")
    public Long save(@PathVariable("challengeId") Long challengeId, TrialRequestDto param) throws IOException, ParseException, JCodecException {
        return trialService.save(challengeId, param);
    }

    @GetMapping("/trials/{trialId}/estimate/detail")
    public Challenge_vs_TrialDto compare(@PathVariable("trialId") Long trialId){
        return trialService.compareDetail(trialId);
    }

    @GetMapping("/trials/{trialId}/estimate")
    public double estimate(@PathVariable("trialId") Long trialId){
        return trialService.runSimilarity(trialId);
    }

    @GetMapping("/challenges/{challengeId}/best_trials")
    public List<TrialResponseDto> getBestTrialList(@PathVariable("challengeId") Long challengeId){
        return trialService.getBestTrials(challengeId);
    }

    @GetMapping("/trials/{trialid}")
    public TrialResponseDto getDetail(@PathVariable("trialid") Long trialId){
        return trialService.getTrial(trialId);
    }

    @GetMapping("/trials/my/{username}")
    public List<TrialResponseDto> getMyList(@PathVariable("username") String username){
        return trialService.getMyList(username);
    }

    @GetMapping("/challenges/{challengeId}/trials")
    public List<TrialResponseDto> getList(@PathVariable("challengeId") Long challengeId){
        return trialService.getTrialList(challengeId);
    }

    @DeleteMapping("/challenges/{challengeId}/trials/{trialid}")
    public Long delete(@PathVariable("trialid") Long trialId){
        return trialService.deleteTrial(trialId);
    }
}