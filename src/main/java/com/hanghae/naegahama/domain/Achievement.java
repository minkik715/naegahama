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
    private Integer achievement1=0;
    @Column
    private Integer achievement2=0;
    @Column
    private Integer achievement3=0;
    @Column
    private Integer achievement4=0;
    @Column
    private Integer achievement5=0;
    @Column
    private Integer achievement6=0;
    @Column
    private Integer achievement7=0;
    @Column
    private Integer achievement8=0;


    public ArrayList<Integer> getAchievementList(){
        return new ArrayList<Integer>(Arrays.asList(achievement1, achievement2, achievement3, achievement4, achievement5,
                achievement6, achievement7, achievement8));
    }

}
