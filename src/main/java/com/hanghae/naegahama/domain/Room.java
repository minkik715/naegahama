package com.hanghae.naegahama.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @NoArgsConstructor
public class Room extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "chat_room_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "room")
    List<Message> messageList = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    List<UserEnterRoom> userEnterRoomList = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }
}
