package com.hanghae.naegahama.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Room extends Timestamped{
    @Id
    @Column(name= "chat_room_id")
    private Long id;

    @OneToMany(mappedBy = "room")
    List<Message> messageList;

    @OneToMany(mappedBy = "room")
    List<UserEnterRoom> userEnterRoomList;
}
