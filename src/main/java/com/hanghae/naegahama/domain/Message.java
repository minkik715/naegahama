package com.hanghae.naegahama.domain;

import javax.persistence.*;

@Entity
public class Message extends Timestamped{

    @Id
    @Column(name = "message_id")
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private MessageType messageType;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "room_id")
    @ManyToOne
    private Room room;
}
