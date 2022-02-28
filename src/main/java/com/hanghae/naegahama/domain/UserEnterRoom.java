package com.hanghae.naegahama.domain;

import javax.persistence.*;

@Entity
public class UserEnterRoom {

    @Id
    @Column(name = "user_enter_room_id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "room_id")
    @ManyToOne
    private Room room;
}
