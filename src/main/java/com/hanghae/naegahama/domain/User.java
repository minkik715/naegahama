package com.hanghae.naegahama.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

<<<<<<< HEAD
    @Column(name = "hippoImage")
    private Integer hippoLevel;                      // 레벨 3달성 추첨이벤트, 슈퍼하마(풀업적) 달성시 이벤트.
=======
//    @Column(name = "hippoImage")
//    private String hippoImage;
>>>>>>> 49af9d9ba6293337da0c2a6b5c70869a8ec51fb6

    @Column
    private int point;



    @Column(nullable = false)
    private String hippoName;    //하마이름이랑 레벨(포인트 = 경험치)를 프론트한테 주기. (노션에 이미지url를 적어드리기)

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
    }

    public User(SignUpRequestDto signUpRequestDto, String password) {
        this.email = signUpRequestDto.getEmail();
        this.nickName = signUpRequestDto.getNickname();
        this.password = password;
<<<<<<< HEAD
        this.hippoLevel = 1;
=======
//        this.hippoImage = "";
>>>>>>> 49af9d9ba6293337da0c2a6b5c70869a8ec51fb6
    }
    public User(KakaoUserInfo kakaoUserInfo) {
        this.email = kakaoUserInfo.getEmail();
        this.nickName = kakaoUserInfo.getNickname();
<<<<<<< HEAD
        this.hippoLevel = 1;
    }
    public void setHippoLevel(Integer hippoLevel) {
        this.hippoLevel = hippoLevel;
    }

    public void setHippoName(String hippoName) {
        this.hippoName = hippoName;
=======
//        this.hippoImage = "";
    }

    public void addPoint(Long answerStar)
    {
        this.point += answerStar*100;
>>>>>>> 49af9d9ba6293337da0c2a6b5c70869a8ec51fb6
    }
}
