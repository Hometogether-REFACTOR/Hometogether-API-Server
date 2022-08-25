package hometoogether.hometoogether.domain.user.domain;

import hometoogether.hometoogether.domain.pose.domain.Pose;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User {

    private final String WITHDRAW_NICKNAME = "탈퇴한 유저";
    private final String DEFAULT_INTRODUCTION = "안녕하세요.";
    private final String DEFAULT_PROFILE_IMAGE_URL = "기본 이미지";

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String nickname;

    private String introduction;

    private String profileImageURL;

    @OneToMany(mappedBy = "user")
    private List<Pose> poses = new ArrayList<>();

    private String salt;

    @Builder
    public User(String username, String password, String nickname, String salt) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.salt = salt;
        this.introduction = DEFAULT_INTRODUCTION;
        this.profileImageURL = DEFAULT_PROFILE_IMAGE_URL;
    }

    public void addPose(Pose pose) {
        this.getPoses().add(pose);
        pose.changeUser(this);
    }

    public void updateProfile(String nickname, String introduction, String profileImageURL) {
        this.nickname = nickname;
        this.introduction = introduction;
        this.profileImageURL = profileImageURL;
    }

    public void deleteUser() {
        this.username = UUID.randomUUID().toString();
        this.password = UUID.randomUUID().toString();
        this.nickname = WITHDRAW_NICKNAME + '_' + UUID.randomUUID();
        this.introduction = DEFAULT_INTRODUCTION;
        this.profileImageURL = DEFAULT_PROFILE_IMAGE_URL;
        for (Pose pose : this.getPoses()) {
            pose.deletePose();
        }
    }
}
