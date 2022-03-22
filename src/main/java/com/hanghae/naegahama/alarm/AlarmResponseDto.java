package com.hanghae.naegahama.alarm;

import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AlarmResponseDto {

    private Long alarmId;
    private String senderNickName;

    private Long receiverId;
    private AlarmType alarmType;
    private Long id;
    private String title;
    private String modifiedAt;
    private ReadingStatus readingStatus;
    private Integer point;

    public AlarmResponseDto(Alarm alarm) {
        this.alarmId = alarm.getAlarmId();
        this.receiverId = alarm.getReceiver().getId();
        this.senderNickName = alarm.getSenderNickName();
        this.alarmType = alarm.getAlarmType();
        this.id = alarm.getId();
        this.title = alarm.getTitle();
        this.modifiedAt = TimeHandler.setModifiedAtComment(alarm.getModifiedAt());
        this.readingStatus = alarm.getReadingStatus();
        this.point = alarm.getPoint();
    }
}

