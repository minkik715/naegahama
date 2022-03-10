package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ranks")
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long id;

    @Column(name = "ranks")
    private int rank;

    @JsonManagedReference
    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    public Rank(User user, int rank) {
        this.user = user;
        this.rank =rank;
    }
}
