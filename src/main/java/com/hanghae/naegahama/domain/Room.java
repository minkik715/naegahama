package com.hanghae.naegahama.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Room extends Timestamped{
    @Id
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
