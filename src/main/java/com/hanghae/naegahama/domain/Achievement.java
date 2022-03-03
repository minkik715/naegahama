package com.hanghae.naegahama.domain;

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
    private Long achievement1;

    @Column
    private Long achievement2;

    @Column
    private Long achievement3;

    @Column
    private Long achievement4;

    @Column
    private Long achievement5;

    @Column
    private Long achievement6;

    @Column
    private Long achievement7;

    @Column
    private Long achievement8;

    @Column
    private Long achievement9;

    @Column
    private Long achievement10;

    @OneToOne ( mappedBy = "achievement") // user entity의 achievement 변수에 종속 당하겠다.
    User user;
}
