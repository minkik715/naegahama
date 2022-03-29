package com.hanghae.naegahama.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Integer achievement1;
    @Column
    private Integer achievement2;
    @Column
    private Integer achievement3;
    @Column
    private Integer achievement4;
    @Column
    private Integer achievement5;
    @Column
    private Integer achievement6;
    @Column
    private Integer achievement7;
    @Column
    private Integer achievement8;


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
        this.achievement8 = 0;
        this.user = user;
    }

    public ArrayList<Integer> getAchievementList(){
        return new ArrayList<Integer>(Arrays.asList(achievement1, achievement2, achievement3, achievement4, achievement5,
                achievement6, achievement7, achievement8));


    }

}
