package hometoogether.hometoogether.domain.heart;

import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {

    private final JwtProvider jwtProvider;
    private final HeartService heartService;

    @PostMapping
    public ResponseEntity<Long> createHeart(CreateHeartReq createHeartReq) {
        User user = jwtProvider.getUserFromHeader();
        return ResponseEntity.ok(heartService.createHeart(user, createHeartReq.getPostId()));
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteHeart(DeleteHeartReq deleteHeartReq) {
        User user = jwtProvider.getUserFromHeader();
        return ResponseEntity.ok(heartService.deleteHeart(user, deleteHeartReq.getPostId()));
    }
}
