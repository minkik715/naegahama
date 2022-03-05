package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class UserEnterRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_enter_room_id")
    private Long id;



    @JsonManagedReference
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JsonManagedReference
    @JoinColumn(name = "room_id")
    @ManyToOne
    private Room room;

    public void setRoomUserStatus(RoomUserStatus roomUserStatus) {
        this.roomUserStatus = roomUserStatus;
    }

    public UserEnterRoom(User user, Room room, RoomUserStatus roomUserStatus) {
        this.user = user;
        this.room = room;
        this.roomUserStatus = roomUserStatus;
    }

    @Column(nullable = false)
    private RoomUserStatus roomUserStatus;
}
