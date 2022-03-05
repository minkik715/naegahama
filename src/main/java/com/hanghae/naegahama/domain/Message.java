package com.hanghae.naegahama.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae.naegahama.dto.message.MessageRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Message extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column
    private MessageType messageType;

    @Column(nullable = false)
    private String time;

    @JsonManagedReference
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JsonManagedReference
    @JoinColumn(name = "room_id")
    @ManyToOne
    private Room room;

    public Message(MessageRequestDto messageRequestDto, User user, Room room,String time) {
        this.message = messageRequestDto.getMessage();
        this.messageType = messageRequestDto.getType();
        this.user = user;
        this.room = room;
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
