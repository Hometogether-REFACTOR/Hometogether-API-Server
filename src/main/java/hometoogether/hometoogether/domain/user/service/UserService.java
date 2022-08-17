package hometoogether.hometoogether.domain.user.service;

import hometoogether.hometoogether.util.JwtUtil;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.dto.JoinReq;
import hometoogether.hometoogether.domain.user.dto.LoginReq;
import hometoogether.hometoogether.domain.user.dto.LoginRes;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import hometoogether.hometoogether.util.Sha256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Sha256Util sha256Util;
    private final JwtUtil jwtUtil;

    @Transactional
    public Long join(JoinReq joinReq) throws NoSuchAlgorithmException {
        validateUserName(joinReq.getUsername());

        String salt = sha256Util.makeSalt();
        return userRepository.save(User.builder()
                .username(joinReq.getUsername())
                .password(sha256Util.sha256(joinReq.getPassword(), salt))
                .salt(salt)
                .build()).getId();
    }

    public LoginRes login(LoginReq loginReq) throws NoSuchAlgorithmException {
        User user = userRepository.findByUsername(loginReq.getUsername()).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        if (!user.getPassword().equals(sha256Util.sha256(loginReq.getPassword(), user.getSalt()))) {
            throw new RuntimeException("잘못된 비밀번호입니다.");
        }
        return LoginRes.builder()
                .accessToken(jwtUtil.generateAccessToken(1L))
                .refreshToken(jwtUtil.generateRefreshToken())
                .build();
    }

    private void validateUserName(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
    }
}
