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

//    @Column(name = "hippoImage")
//    private String hippoImage;

    @Column
    private int point;

//    @OneToOne(mappedBy = "user")
//    private Survey Survey;

 /*   @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();*/

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

    @OneToOne(mappedBy = "user")
    @JoinColumn ( name = "achievement_id")
    private Achievement achievement;

    @OneToOne(mappedBy = "user")
//    @JoinColumn ( name = "hippoinfo_id")
    private Hippoinfo hippoInfo;


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
//        this.hippoImage = "";
    }
    public User(KakaoUserInfo kakaoUserInfo) {
        this.email = kakaoUserInfo.getEmail();
        this.nickName = kakaoUserInfo.getNickname();
//        this.hippoImage = "";
    }

    public void addPoint(Long answerStar)
    {
        this.point += answerStar*100;
    }
}
