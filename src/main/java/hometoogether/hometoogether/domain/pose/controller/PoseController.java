package hometoogether.hometoogether.domain.pose.controller;

import hometoogether.hometoogether.domain.pose.dto.ReadPoseListRes;
import hometoogether.hometoogether.util.JwtProvider;
import hometoogether.hometoogether.domain.pose.dto.UploadPoseReq;
import hometoogether.hometoogether.domain.pose.service.PoseService;
import hometoogether.hometoogether.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/poses")
public class PoseController {

    private final JwtProvider jwtProvider;
    private final PoseService poseService;

    @PostMapping
    public ResponseEntity uploadPose(@RequestBody UploadPoseReq uploadPoseReq) throws IOException {
        User user = jwtProvider.getUserFromHeader();
        return ResponseEntity.ok(poseService.uploadPose(user, uploadPoseReq.getMultipartFile()));
    }

    @GetMapping
    public ResponseEntity<ReadPoseListRes> readPose(Pageable pageable) {
        return ResponseEntity.ok(poseService.readPose(pageable));
    }
}
