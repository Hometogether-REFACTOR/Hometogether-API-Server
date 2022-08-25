package hometoogether.hometoogether.domain.user.service;

import hometoogether.hometoogether.domain.user.dto.*;
import hometoogether.hometoogether.util.JwtProvider;
import hometoogether.hometoogether.domain.user.domain.User;
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
    private final JwtProvider jwtProvider;

    @Transactional
    public User join(JoinReq joinReq) throws NoSuchAlgorithmException {
        validateName(joinReq.getUsername());
        validateName(joinReq.getNickname());

        String salt = sha256Util.makeSalt();
        String saltedPassword = sha256Util.sha256(joinReq.getPassword(), salt);
        return userRepository.save(joinReq.toEntity(saltedPassword, salt));
    }

    public Boolean validateName(String name) {
        if (userRepository.findByUsername(name).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.findByNickname(name).isPresent()) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }
        return true;
    }

    public LoginRes login(LoginReq loginReq) throws NoSuchAlgorithmException {
        User user = userRepository.findByUsername(loginReq.getUsername()).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        if (!user.getPassword().equals(sha256Util.sha256(loginReq.getPassword(), user.getSalt()))) {
            throw new RuntimeException("잘못된 비밀번호입니다.");
        }
        return new LoginRes(jwtProvider.createAccessToken(user.getId()), jwtProvider.createRefreshToken(user.getId()));
    }

    public LoginRes reIssue(String username, String refreshToken) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        jwtProvider.validateRefreshToken(user.getId().toString(), refreshToken);
        return new LoginRes(jwtProvider.createAccessToken(user.getId()), refreshToken);
    }

    public void logout(User user, String accessToken) {
        jwtProvider.blackListToken(user.getId(), accessToken);
    }

    @Transactional
    public Long updateProfile(User user, UpdateProfileReq updateProfileReq) {
        user.updateProfile(updateProfileReq.getNickname(), updateProfileReq.getIntroduction(), updateProfileReq.getProfileImageURL());
        return user.getId();
    }

    public ReadProfileRes readProfile(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        return new ReadProfileRes(user.getNickname(), user.getIntroduction(), user.getProfileImageURL());
    }

    @Transactional
    public Long withdraw(User user, String accessToken) {
        user.deleteUser();
        jwtProvider.blackListToken(user.getId(), accessToken);
        return user.getId();
    }
}
