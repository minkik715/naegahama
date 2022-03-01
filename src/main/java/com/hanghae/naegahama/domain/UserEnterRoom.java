package com.hanghae.naegahama.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
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

    public void setRoomUserStatus(RoomUserStatus roomUserStatus) {
        this.roomUserStatus = roomUserStatus;
    }

    @Column(nullable = false)
    private RoomUserStatus roomUserStatus;
}
