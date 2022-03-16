package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae.naegahama.dto.signup.SignUpRequestDto;
import com.hanghae.naegahama.dto.user.UserInfoRequestDto;
import com.hanghae.naegahama.dto.user.KakaoUserInfoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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

    @Column(unique = true)
    private String nickName;

    @Column(name = "password")
    private String password;

    @Column
    private Integer hippoLevel;                      // 레벨 3달성 추첨이벤트, 슈퍼하마(풀업적) 달성시 이벤트.

    @Column
    private int point;

    @Column
    private Long kakaoId;

    @Column
    private String hippoName;  //하마이름이랑 레벨(포인트 = 경험치)를 프론트한테 주기. (노션에 이미지url를 적어드리기)

    @Column
    private String category;

    @Column
    private String gender;

    @Column
    private String age;

    @Column
    private String userStatus;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    @JsonBackReference
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Comment> commentList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<PostLike> postLikeList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Answer> answerList = new ArrayList<>();


    @JsonManagedReference
    @OneToOne
    @JoinColumn ( name = "achievement_id")
    private Achievement achievement;


    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Search> searchWord = new ArrayList<>();


    public User(String email, String nickName, String password, int point) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.point = point;
        this.hippoLevel = 1;
        this.userStatus = "true";
    }

    public User(SignUpRequestDto signUpRequestDto, String password) {
        this.email = signUpRequestDto.getEmail();
        this.nickName = signUpRequestDto.getNickname();
        this.password = password;
        this.hippoLevel = 1;
        this.userStatus = "true";


    }
    public User(KakaoUserInfoDto kakaoUserInfoDto) {
        this.email = kakaoUserInfoDto.getEmail();
        this.nickName = kakaoUserInfoDto.getNickname();
        this.kakaoId = kakaoUserInfoDto.getId();
        this.hippoLevel = 1;
        this.userStatus = "true";
    }


    public User(String encodedPassword, String email, UserRoleEnum role, Long kakaoId) {
        this.password = encodedPassword;
        this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
    }

    @Transactional
    public void setBasicInfo(UserInfoRequestDto userInfoRequestDto){
        this.nickName = userInfoRequestDto.getNickname();
        this.gender = userInfoRequestDto.getGender();
        this.age = userInfoRequestDto.getAge();
        this.category = userInfoRequestDto.getCategory();
        this.userStatus = "false";
    }
    public void setHippoLevel(Integer hippoLevel) {
        this.hippoLevel = hippoLevel;
    }

    public void setHippoName(String hippoName) {
        this.hippoName = hippoName;
    }

    public void addPoint(Integer point)
    {
        this.point += point;

        // 하마 레벨이 3(최대레벨) 이라면 if문을 타지 않고 끝
        if (this.hippoLevel != 3 )
        {
            if (this.point >= 2000)
            {
                this.hippoLevel = 3;
            }
            else if ( this.point >= 1000 && this.hippoLevel !=2)
            {
                this.hippoLevel = 2;
            }
        }

    }

    public void setAchievement(Achievement achievement)
    {
        this.achievement = achievement;
    }

    public void setSearch(List<Search> searchWord) {
        this.searchWord = searchWord;
    }
}
