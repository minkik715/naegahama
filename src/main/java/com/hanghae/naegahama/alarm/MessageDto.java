
package com.hanghae.naegahama.alarm;

import com.hanghae.naegahama.util.TimeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto implements Serializable {
    private Long alarmId;
    private Long receiverId;
    private String senderNickName;
    private AlarmType alarmType;
    private Long id;
    private String title;
    String modifiedAt;
    private ReadingStatus readingStatus;

    public MessageDto (Alarm alarm) {
        this.receiverId = alarm.getReceiver().getId();
        this.alarmId = alarm.getAlarmId();
        this.senderNickName = alarm.getSenderNickName();
        this.alarmType = alarm.getAlarmType();
        this.id = alarm.getId();
        this.title = alarm.getTitle();
        this.modifiedAt = TimeHandler.setModifiedAtComment(alarm.getModifiedAt());
        this.readingStatus = alarm.getReadingStatus();
    }
}

//메세지를 작성하면, alarmService를 이용해서 메시지에 의한 알람을 발송해주겠습니다.