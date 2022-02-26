package com.hanghae.naegahama.domain;


import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column( nullable = false, unique = true)
    private String email;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "password")
    private String password;


}
