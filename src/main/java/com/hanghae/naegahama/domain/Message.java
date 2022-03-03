package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.naegahama.dto.message.MessageRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Message extends Timestamped{

    @Id
    @Column(name = "message_id")
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private MessageType messageType;

    @Column(nullable = false)
    private String time;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "room_id")
    @ManyToOne
    private Room room;

    public Message(MessageRequestDto messageRequestDto, User user, Room room,String time) {
        this.message = messageRequestDto.getMessage();
        this.messageType = messageRequestDto.getType();
        this.user = user;
        user.getMessageList().add(this);
        this.room = room;
        room.getMessageList().add(this);
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
