/*
package com.hanghae.naegahama.alarm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable {
    private Long id;
    private Long receiverId;
    private String message;
    private ReadingStatus readingStatus;
    LocalDateTime createdAt;

    public MessageDto (Message message) {
        this.id = message.getId();
        this.receiverId = message.getReceiver().getId();
        this.message = message.getMessage();
        this.readingStatus = message.getReadingStatus();
        this.createdAt = message.getCreatedAt();
    }
}

//메세지를 작성하면, alarmService를 이용해서 메시지에 의한 알람을 발송해주겠습니다.*/
