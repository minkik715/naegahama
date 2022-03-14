package com.hanghae.naegahama.domain;

import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tools.ant.taskdefs.Get;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Achievement extends Timestamped
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_id", nullable = false)
    private Long id;

    @Column
    private int achievement1;
    @Column
    private int achievement2;
    @Column
    private int achievement3;
    @Column
    private int achievement4;
    @Column
    private int achievement5;
    @Column
    private int achievement6;
    @Column
    private int achievement7;
    @Column
    private int achievement8;

//    @Column
//    private int firstAnswerWrite;


    @OneToOne ( mappedBy = "achievement") // user entity의 achievement 변수에 종속 당하겠다.
    User user;

    public Achievement(User user)
    {
        this.achievement1 = 0;
        this.achievement2 = 0;
        this.achievement3 = 0;
        this.achievement4 = 0;
        this.achievement5 = 0;
        this.achievement6 = 0;
        this.achievement7 = 0;
//        this.firstAnswerWrite = 0;
        this.user = user;
        user.setAchievement(this);
    }

    public void AddAchievement(int AchievementNum) {
        switch (AchievementNum) {
            case 1:
                if (this.getAchievement1() == 0)
                {
                    this.achievement1 = 1;
                }
                break;
            case 2:
                if (this.getAchievement2() == 0)
                {
                    this.achievement2 = 1;
                }
                break;
            case 3:
                if (this.getAchievement3() == 0)
                {
                    this.achievement3 = 1;
                }
                break;
            case 4:
                if (this.getAchievement4() == 0)
                {
                    this.achievement4 = 1;
                }
                break;
            case 5:
                if (this.getAchievement5() == 0)
                {
                    this.achievement5 = 1;
                }
                break;
            case 6:
                if (this.getAchievement6() == 0)
                {
                    this.achievement6 = 1;
                }
                break;
            case 7:
                if (this.getAchievement7() == 0)
                {
                    this.achievement7 = 1;
                }
                break;
//            case 8:
//                if (this.getFirstAnswerWrite() == 0)
//                {
//                    this.firstAnswerWrite = 1;
//                }
//                break;
            default:
                break;
        }
    }


}
