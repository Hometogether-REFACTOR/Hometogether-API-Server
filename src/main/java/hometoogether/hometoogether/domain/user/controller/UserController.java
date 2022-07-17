package hometoogether.hometoogether.domain.user.controller;

import hometoogether.hometoogether.common.ApiResponse;
import hometoogether.hometoogether.common.JwtAuthenticationResponse;
import hometoogether.hometoogether.config.jwt.JwtTokenProvider;
import hometoogether.hometoogether.domain.user.domain.*;
import hometoogether.hometoogether.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws Exception {
        return ResponseEntity.ok(new JwtAuthenticationResponse(userService.signIn(loginRequest)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        String result = userService.singUp(signUpRequest);

        if (result.equals("EMAIL EXIST")) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        } else if (result.equals("USERNAME EXIST")) {
            return new ResponseEntity(new ApiResponse(false, "User Name already in use!"), HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.created(URI.create(result)).body(new ApiResponse(true, "User registered successfully"));
        }
    }

    @PatchMapping("/find/password")
    public ResponseEntity<?> findPassword(@RequestBody PasswordFindReqeust passwordFindReqeust) throws Exception {
        return ResponseEntity.ok(userService.resetPassword(passwordFindReqeust));
    }

    @GetMapping("/find/userName/{email}")
    public String findUser(@PathVariable String email) {
        return userService.findUser(email);
    }

    @DeleteMapping("/withdraw/{email}")
    public String withdrawUser(@PathVariable String email) {
        userService.deleteUser(email);
        return "SUCCESS";
    }
}
