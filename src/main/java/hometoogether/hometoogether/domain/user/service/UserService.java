package hometoogether.hometoogether.domain.user.service;

import hometoogether.hometoogether.config.jwt.JwtService;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.dto.JoinReqDto;
import hometoogether.hometoogether.domain.user.dto.LoginReqDto;
import hometoogether.hometoogether.domain.user.dto.LoginResDto;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService tokenProvider;

    @Transactional
    public Long join(JoinReqDto joinReqDto) {
        if (!isValidUserName(joinReqDto.getUsername())) {
            throw new RuntimeException();
        }
        if (!isValidPassword(joinReqDto.getPassword())) {
            throw new RuntimeException();
        }
        return userRepository.save(joinReqDto.toEntity(encodePassword(joinReqDto.getPassword()))).getId();
    }

    public LoginResDto login(LoginReqDto loginReqDto) {
        User user = userRepository.findByUsername(loginReqDto.getUsername()).orElseThrow(() -> new RuntimeException());
        if (!loginReqDto.getPassword().equals(user.getPassword())) {
            throw new RuntimeException();
        }
        return LoginResDto.builder()
                .accessToken(tokenProvider.generateAccessToken(1L))
                .refreshToken(tokenProvider.generateRefreshToken())
                .build();
    }

    private Boolean isValidUserName(String username) {
        return null;
    }

    private Boolean isValidPassword(String password) {
        return null;
    }

    private String encodePassword(String password) {
        return null;
    }
}
