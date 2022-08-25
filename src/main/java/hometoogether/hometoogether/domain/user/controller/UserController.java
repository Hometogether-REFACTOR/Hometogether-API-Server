package hometoogether.hometoogether.domain.user.controller;

import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.dto.*;
import hometoogether.hometoogether.domain.user.service.UserService;
import hometoogether.hometoogether.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody JoinReq joinReq) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.join(joinReq).getId());
    }

    @GetMapping("/validation/{name}")
    public ResponseEntity<Boolean> validateName(@PathVariable("name") String name) {
        return ResponseEntity.ok(userService.validateName(name));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.login(loginReq));
    }

    @GetMapping("/re-issue")
    public ResponseEntity<LoginRes> reIssue(@RequestParam String username, @RequestParam String refreshToken) {
        return ResponseEntity.ok(userService.reIssue(username, refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestParam String accessToken) {
        User user = jwtProvider.getUserFromHeader();
        userService.logout(user, accessToken);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<Long> updateProfile(@RequestBody UpdateProfileReq updateProfileReq) {
        User user = jwtProvider.getUserFromHeader();
        return ResponseEntity.ok(userService.updateProfile(user, updateProfileReq));
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<ReadProfileRes> readProfile(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.readProfile(username));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Long> withdraw(@RequestParam String accessToken) {
        User user = jwtProvider.getUserFromHeader();
        return ResponseEntity.ok(userService.withdraw(user, accessToken));
    }
}
