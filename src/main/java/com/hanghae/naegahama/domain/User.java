package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae.naegahama.dto.user.UserInfoRequestDto;
import com.hanghae.naegahama.initial.HippoURL;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Slf4j
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
    private String hippoImage;

    @Column
    private String category;

    @Column
    private String gender;

    @Column
    private String age;

    @Column
    private String phoneNumber;

    @Column
    private String userStatus;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    // 내가 쓴 댓글
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

    // 나에게 달린 댓글
    @JsonBackReference
    @OneToMany(mappedBy = "writer")
    private final List<UserComment> mycomment = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Search> searchWord = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "receiver")
    private List<Alarm> alarmList = new ArrayList<>();

    public User(String encodedPassword, String email, UserRoleEnum role, Long kakaoId) {
        this.password = encodedPassword;
        this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
        this.hippoImage = HippoURL.basicHippoURL[0];
        this.userStatus = "true";
        this.hippoName = "일반 하마";
        this.hippoLevel = 1;
    }

    @Transactional
    public void setBasicInfo(UserInfoRequestDto userInfoRequestDto){
        this.nickName = userInfoRequestDto.getNickname();
        this.gender = userInfoRequestDto.getGender();
        this.age = userInfoRequestDto.getAge();
        this.category = userInfoRequestDto.getCategory();
        this.phoneNumber = userInfoRequestDto.getPhone();
        this.userStatus = "false";
    }

    public void setHippoName(String hippoName) {
        this.hippoName = hippoName;
        this.hippoImage = HippoURL.name(hippoName,this.getHippoLevel());
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public List<Alarm> sendAlarm(Integer point, AlarmType alarmType, Object object) {
        List<Alarm> alarmList = new ArrayList<>();
        // 하마 레벨이 3(최대레벨) 이라면 if문을 타지 않고 끝
        //도메인에 있어서 어떻게 할수가 없네요 적용 불가...
        log.info("재균님의 레벨은 ? = {}", this.hippoLevel);
        if (this.hippoLevel != 3) {
            if (this.point >= 2000) {
                this.hippoLevel = 3;
                log.info("@@@@@진화@@@@@@@@@@ 2->3");
                this.hippoImage=HippoURL.name(this.getHippoName(),this.getHippoLevel());
                Alarm alarm = new Alarm(this, null, AlarmType.level, (long) this.hippoLevel, "레벨업!");
                alarmList.add(alarm);
            }
            else if ( this.point >= 1000 && this.hippoLevel !=2)
            {
                this.hippoLevel = 2;
                this.hippoImage=HippoURL.name(this.getHippoName(),this.getHippoLevel());
                log.info("@@@@@진화@@@@@@@@@@ 1->2");
                Alarm alarm = new Alarm(this, null, AlarmType.level, (long) this.hippoLevel, "레벨업!");
                alarmList.add(alarm);
            }
        }
        log.info("재균님의 레벨은 ? = {}", this.hippoLevel);
        if(alarmType.equals(AlarmType.pointR) ||alarmType.equals(AlarmType.pointAL) || (alarmType.equals(AlarmType.pointA) || alarmType.equals(AlarmType.pointRD))){
            Answer answer = (Answer) object;
            Alarm alarm = new Alarm(this, null, alarmType, answer.getId(), answer.getTitle(),point);
            alarmList.add(alarm);
        }else if(alarmType.equals(AlarmType.pointPL)){
            Post post = (Post) object;
            Alarm alarm = new Alarm(this, null, alarmType,post.getId(), post.getTitle(),point);
            alarmList.add(alarm);
        }
        return alarmList;
    }




    public void setAchievement(Achievement achievement)
    {
        this.achievement = achievement;
    }


}
