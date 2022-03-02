package com.hanghae.naegahama.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long id;

    @Column
    private int rank;

    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

}
