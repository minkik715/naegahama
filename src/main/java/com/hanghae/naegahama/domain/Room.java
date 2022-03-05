package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @OneToOne(mappedBy = "room")
    private Post post;

    @JsonBackReference
    @OneToMany(mappedBy = "room")
    List<UserEnterRoom> userEnterRoomList = new ArrayList<>();

    public Room(String name, Post post) {
        this.name = name;
        this.post = post;
        post.setRoom(this);
    }
}
