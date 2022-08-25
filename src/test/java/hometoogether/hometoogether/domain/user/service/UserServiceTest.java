package hometoogether.hometoogether.domain.user.service;

import hometoogether.hometoogether.domain.user.dto.ReadProfileRes;
import hometoogether.hometoogether.domain.user.dto.UpdateProfileReq;
import hometoogether.hometoogether.util.JwtProvider;
import hometoogether.hometoogether.domain.user.domain.User;
import hometoogether.hometoogether.domain.user.dto.JoinReq;
import hometoogether.hometoogether.domain.user.dto.LoginReq;
import hometoogether.hometoogether.domain.user.repository.UserRepository;
import hometoogether.hometoogether.util.Sha256Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Sha256Util sha256Util;
    @Mock
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("회원 가입을 성공한다.")
    void join1() throws NoSuchAlgorithmException {
        //given
        JoinReq joinReq = new JoinReq("userA", "1234", "nick");

        String salt = doReturn("salt").when(sha256Util).makeSalt();
        String saltedPassword = doReturn("saltedPassword").when(sha256Util).sha256(joinReq.getPassword(), "salt");

        doReturn(Optional.ofNullable(null)).when(userRepository).findByUsername(anyString());
        doReturn(Optional.ofNullable(null)).when(userRepository).findByNickname(anyString());

        doReturn(User.builder()
                .username(joinReq.getUsername())
                .password(saltedPassword)
                .salt(salt)
                .nickname(joinReq.getNickname())
                .build()).when(userRepository).save(any(User.class));

        //when
        User user = userService.join(joinReq);

        //then
        assertEquals(user.getUsername(), joinReq.getUsername());
        assertEquals(user.getPassword(), saltedPassword);
    }

    @Test
    @DisplayName("같은 닉네임으로 회원 가입을 시도하면 실패한다.")
    void join2() {
        //given
        doReturn(Optional.ofNullable(null)).when(userRepository).findByUsername(anyString());
        doReturn(Optional.of(User.builder()
                .username("userA")
                .password("1234")
                .salt("1234")
                .nickname("nick")
                .build())).when(userRepository).findByNickname(any(String.class));

        JoinReq joinReq = new JoinReq("userB", "5678", "nick");

        //when, then
        assertThrows(RuntimeException.class, () -> userService.join(joinReq));
    }

    @Test
    @DisplayName("로그인을 성공한다.")
    void login() throws NoSuchAlgorithmException {
        //given
        LoginReq loginReq = new LoginReq("userA", "1234");
        doReturn(Optional.of(User.builder()
                .username("userA")
                .password("saltedPassword")
                .salt("salt")
                .nickname("nick")
                .build())).when(userRepository).findByUsername(anyString());
        doReturn("saltedPassword").when(sha256Util).sha256(anyString(), anyString());

        //when, then
        assertDoesNotThrow(() -> userService.login(loginReq));
    }

    @Test
    @DisplayName("프로필을 수정할 수 있다.")
    void update() {
        //given
        User user = User.builder()
                .username("userA")
                .password("saltedPassword")
                .salt("salt")
                .nickname("nick")
                .build();

        UpdateProfileReq updateProfileReq = new UpdateProfileReq("newNick", "hello", "flower");

        //when
        userService.updateProfile(user, updateProfileReq);

        //then
        assertEquals(updateProfileReq.getNickname(), user.getNickname());
        assertEquals(updateProfileReq.getIntroduction(), user.getIntroduction());
        assertEquals(updateProfileReq.getProfileImageURL(), user.getProfileImageURL());
    }

    @Test
    @DisplayName("프로필을 조회할 수 있다.")
    void profile() {
        //given
        doReturn(Optional.of(User.builder()
                .username("userA")
                .password("saltedPassword")
                .salt("salt")
                .nickname("nick")
                .build())).when(userRepository).findByUsername(anyString());

        //when
        ReadProfileRes readProfileRes = userService.readProfile("userA");

        //then
        assertEquals("nick", readProfileRes.getNickname());
    }

    @Test
    @DisplayName("탈퇴할 수 있다.")
    void withdraw() {
        //given
        User user = User.builder()
                .username("userA")
                .password("saltedPassword")
                .salt("salt")
                .nickname("nick")
                .build();

        //when
        userService.withdraw(user, "");

        //then
        assertTrue(user.getNickname().startsWith("탈퇴한 유저"));
    }
}
