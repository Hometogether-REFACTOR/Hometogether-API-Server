package hometoogether.hometoogether.domain.user.controller;

import hometoogether.hometoogether.domain.user.domain.*;
import hometoogether.hometoogether.domain.user.dto.JoinReqDto;
import hometoogether.hometoogether.domain.user.dto.LoginReqDto;
import hometoogether.hometoogether.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody JoinReqDto joinReqDto) {
        return ResponseEntity.ok(userService.join(joinReqDto));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginReqDto loginReqDto) {
        return ResponseEntity.ok(userService.login(loginReqDto));
    }

    @PatchMapping("/find/password")
    public ResponseEntity<?> findPassword(@RequestBody PasswordFindReqeust passwordFindReqeust) throws Exception {
        return ResponseEntity.ok(userService.resetPassword(passwordFindReqeust));
    }

    @DeleteMapping("/withdraw/{email}")
    public String withdrawUser(@PathVariable String email) {
        userService.deleteUser(email);
        return "SUCCESS";
    }
}
