
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
    private Long alarmId;
    private Long receiverId;
    private String senderNickName;
    private Type type;
    private Long id;
    private String title;
    String modifiedAt;
    private ReadingStatus readingStatus;

    public MessageDto (Alarm alarm) {
        this.receiverId = alarm.getReceiver().getId();
        this.alarmId = alarm.getAlarmId();
        this.senderNickName = alarm.getSenderNickName();
        this.type = alarm.getType();
        this.id = alarm.getId();
        this.title = alarm.getTitle();
        this.modifiedAt = alarm.getModifiedAt().toString();
        this.readingStatus = alarm.getReadingStatus();
    }
}

//메세지를 작성하면, alarmService를 이용해서 메시지에 의한 알람을 발송해주겠습니다.