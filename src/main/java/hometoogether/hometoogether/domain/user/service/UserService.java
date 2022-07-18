package hometoogether.hometoogether.domain.user.service;

import hometoogether.hometoogether.domain.user.domain.*;
import hometoogether.hometoogether.domain.user.dto.JoinReqDto;
import hometoogether.hometoogether.domain.user.dto.LoginReqDto;
import hometoogether.hometoogether.domain.user.dto.LoginResDto;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final JwtTokenProvider tokenProvider;

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
        return null;
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

    @Transactional
    public String resetPassword(PasswordFindReqeust passwordFindReqeust) throws Exception {
        User user = userRepository.findByEmail(passwordFindReqeust.getEmail());

        if (user == null) {
            throw new Exception("유저가 없습니다.");
        }

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

//        user.update(passwordEncoder.encode(generatedString));
        return generatedString;
    }

    @Transactional
    public void deleteUser(String userName) {
        //TODO: 챌린지(Trials)및 루틴 삭제 기능 필요
        userRepository.deleteByUserName(userName);
    }
}
