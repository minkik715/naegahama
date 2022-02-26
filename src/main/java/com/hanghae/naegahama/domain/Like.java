package com.hanghae.naegahama.domain;

import javax.persistence.*;

@Table(name = "likes")
@Entity
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_id", nullable = false)
    private Long id;


}
