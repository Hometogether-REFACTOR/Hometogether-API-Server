package hometoogether.hometoogether.domain.user.controller;

import hometoogether.hometoogether.domain.user.dto.JoinReq;
import hometoogether.hometoogether.domain.user.dto.LoginReq;
import hometoogether.hometoogether.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@CrossOrigin("*")
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
    public ResponseEntity login(@RequestBody LoginReq loginReq) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.login(loginReq));
    }
}
