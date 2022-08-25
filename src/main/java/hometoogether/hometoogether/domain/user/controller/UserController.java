package hometoogether.hometoogether.domain.user.controller;

import hometoogether.hometoogether.domain.user.dto.JoinReq;
import hometoogether.hometoogether.domain.user.dto.LoginReq;
import hometoogether.hometoogether.domain.user.dto.LoginRes;
import hometoogether.hometoogether.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody JoinReq joinReq) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.join(joinReq));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.login(loginReq));
    }

    @GetMapping("/re-issue")
    public ResponseEntity<LoginRes> reIssue(@RequestParam String username, @RequestParam String refreshToken) {
        return ResponseEntity.ok(userService.reIssue(username, refreshToken));
    }
}
