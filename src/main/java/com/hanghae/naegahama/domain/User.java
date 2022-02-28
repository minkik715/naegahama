package com.hanghae.naegahama.domain;


import com.hanghae.naegahama.dto.signup.SignUpRequestDto;
import com.hanghae.naegahama.kakaologin.KakaoUserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column( nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickName;

    @Column(name = "password")
    private String password;

    @Column(name = "hippoImage")
    private String hippoImage;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user")
    private List<Like> likeList;

    @OneToMany(mappedBy = "user")
    private List<Message> messageList;

    @OneToMany(mappedBy = "user")
    private List<UserEnterRoom> userEnterRoomList;

    public User(SignUpRequestDto signUpRequestDto,String password) {
        this.email = signUpRequestDto.getEmail();
        this.nickName = signUpRequestDto.getNickname();
        this.password = password;
        this.hippoImage = "";
    }
    public User(KakaoUserInfo kakaoUserInfo) {
        this.email = kakaoUserInfo.getEmail();
        this.nickName = kakaoUserInfo.getNickname();
        this.hippoImage = "";
    }
}
