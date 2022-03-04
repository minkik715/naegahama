package com.hanghae.naegahama.domain;

import com.hanghae.naegahama.dto.signup.SignUpRequestDto;
import com.hanghae.naegahama.kakaologin.KakaoUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column( nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickName;

    @Column(name = "password")
    private String password;

    @Column
    private Integer hippoLevel;                      // 레벨 3달성 추첨이벤트, 슈퍼하마(풀업적) 달성시 이벤트.

    @Column
    private int point;

    @Column
    private String hipponame;    //하마이름이랑 레벨(포인트 = 경험치)를 프론트한테 주기. (노션에 이미지url를 적어드리기)

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PostLike> postLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Message> messageList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserEnterRoom> userEnterRoomList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Answer> answerList = new ArrayList<>();

    @OneToOne
    @JoinColumn ( name = "achievement_id")
    private Achievement achievement;

    public User(String email, String nickName, String password, int point) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.point = point;
        this.hippoLevel = 1;

    }

    public User(SignUpRequestDto signUpRequestDto, String password) {
        this.email = signUpRequestDto.getEmail();
        this.nickName = signUpRequestDto.getNickname();
        this.password = password;
        this.hippoLevel = 1;


    }
    public User(KakaoUserInfo kakaoUserInfo) {
        this.email = kakaoUserInfo.getEmail();
        this.nickName = kakaoUserInfo.getNickname();
        this.hippoLevel = 1;

    }
    public void setHippoLevel(Integer hippoLevel) {
        this.hippoLevel = hippoLevel;
    }

    public void setHippoName(String hipponame) {
        this.hipponame = hipponame;
    }

    public void addPoint(Long answerStar)
    {
        this.point += answerStar*100;
    }

    public void setAchievement(Achievement achievement)
    {
        this.achievement = achievement;
    }
}
