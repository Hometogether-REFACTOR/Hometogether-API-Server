package hometoogether.hometoogether.domain.user.domain;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final String WITHDRAW_NICKNAME = "탈퇴한 유저";
    private final String DEFAULT_INTRODUCTION = "안녕하세요.";
    private final String DEFAULT_PROFILE_IMAGE_URL = "기본 이미지";
    private final String DELETED_FILE_URL = "삭제된 이미지";

    @Test
    @DisplayName("유저를 생성하면 기본 프로필로 저장되어야 한다.")
    public void createUser() {
        //given
        User user = getTestUser();

        //when, then
        assertEquals(DEFAULT_INTRODUCTION, user.getDEFAULT_INTRODUCTION());
        assertEquals(DEFAULT_PROFILE_IMAGE_URL, user.getDEFAULT_PROFILE_IMAGE_URL());
    }

    @Test
    @DisplayName("프로필을 수정할 수 있다.")
    public void updateProfile() {
        //given
        User user = getTestUser();

        //when
        user.updateProfile("lee", "hi", "flower");

        //then
        assertEquals("lee", user.getNickname());
        assertEquals("hi", user.getIntroduction());
        assertEquals("flower", user.getProfileImageURL());
    }

    @Test
    @DisplayName("유저와 포즈는 양방향 참조 가능하다.")
    public void addPose() {
        //given
        User user = getTestUser();
        Pose pose = getTestPose();

        //when
        user.addPose(pose);

        //then
        assertEquals("PUSH UP", user.getPoses().get(0).getS3FileName());
        assertEquals("kim", pose.getUser().getUsername());
    }

    @Test
    @DisplayName("유저를 삭제하면 프로필과 포즈가 기본값으로 변경된다.")
    public void deleteUser() {
        //given
        User user = getTestUser();
        Pose pose = getTestPose();
        user.addPose(pose);

        //when
        user.updateProfile("lee", "hi", "flower");
        user.deleteUser();

        //then
        assertTrue(user.getNickname().startsWith(WITHDRAW_NICKNAME));
        assertEquals(DEFAULT_INTRODUCTION, user.getIntroduction());
        assertEquals(DEFAULT_PROFILE_IMAGE_URL, user.getProfileImageURL());
        assertEquals(DELETED_FILE_URL, pose.getS3FileName());
    }

    private User getTestUser() {
        return User.builder()
                .username("kim")
                .password("1234")
                .salt("qwer")
                .build();
    }

    private Pose getTestPose() {
        return Pose.builder()
                .s3FileName("PUSH UP")
                .build();
    }
}
