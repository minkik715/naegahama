package com.hanghae.naegahama.alarm;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae.naegahama.domain.Timestamped;
import com.hanghae.naegahama.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Alarm extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long alarmId;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "sender_nickName")
    private String senderNickName;

    @Column
    private Type type;

    @Column
    private Long id;

    @Column
    private String title;


    @Enumerated(EnumType.STRING)
    private ReadingStatus readingStatus;


    public Alarm(User receiver, String senderNickName, Type type, Long id, String title) {
        this.receiver = receiver;
        this.senderNickName = senderNickName;
        this.type = type;
        this.id = id;
        this.title = title;
        this.readingStatus = ReadingStatus.N;
    }

    public void changeReadingStatus(ReadingStatus readingStatus) {
        this.readingStatus = readingStatus;
    }

    public LocalDateTime modifiedAt() {
        return null;
    }
}
