package com.hanghae.naegahama.domain;

import com.hanghae.naegahama.dto.answer.AnswerPostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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
    private int firstAnswerWrite;


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
        this.firstAnswerWrite = 0;
        this.user = user;
        user.setAchievement(this);
    }

}
