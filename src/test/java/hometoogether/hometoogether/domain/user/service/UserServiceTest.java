package hometoogether.hometoogether.domain.user.service;

import hometoogether.hometoogether.util.JwtUtil;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.dto.JoinReq;
import hometoogether.hometoogether.domain.user.dto.LoginReq;
import hometoogether.hometoogether.domain.user.dto.LoginRes;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void 회원가입_성공() throws NoSuchAlgorithmException {
        //given
        JoinReq joinReq = new JoinReq();
        joinReq.setUsername("kim");
        joinReq.setPassword("1234");

        //when
        Long userId = userService.join(joinReq);

        //then
        Optional<User> user = userRepository.findById(userId);
        assertNotNull(user);
        assertEquals(user.get().getUsername(), joinReq.getUsername());
    }

    @Test
    void 회원가입_실패() throws NoSuchAlgorithmException {
        //given
        JoinReq joinReq1 = new JoinReq();
        joinReq1.setUsername("kim");
        joinReq1.setPassword("1234");
        userService.join(joinReq1);

        //when
        JoinReq joinReq2 = new JoinReq();
        joinReq2.setUsername("kim");
        joinReq2.setPassword("1234");

        //then
        assertThrows(RuntimeException.class, () -> {
            userService.join(joinReq2);
        });
    }

    @Test
    void 로그인_성공() throws NoSuchAlgorithmException {
        //given
        JoinReq joinReq = new JoinReq();
        joinReq.setUsername("kim");
        joinReq.setPassword("1234");
        userService.join(joinReq);

        //when
        LoginReq loginReq = new LoginReq();
        loginReq.setUsername("kim");
        loginReq.setPassword("1234");
        LoginRes loginRes = userService.login(loginReq);

        //then
        assertEquals(jwtUtil.validateToken(loginRes.getAccessToken()), true);
    }

    @Test
    void 로그인_실패() throws NoSuchAlgorithmException {
        //given
        JoinReq joinReq = new JoinReq();
        joinReq.setUsername("kim");
        joinReq.setPassword("1234");
        userService.join(joinReq);

        //when
        LoginReq loginReq = new LoginReq();
        loginReq.setUsername("kim");
        loginReq.setPassword("1235");

        //then
        assertThrows(RuntimeException.class, () -> {
            userService.login(loginReq);
        });
    }
}
