package com.hanghae.naegahama.dto.alarm;

import com.hanghae.naegahama.domain.Alarm;
import com.hanghae.naegahama.dto.alarm.ReadingStatus;
import com.hanghae.naegahama.dto.alarm.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDto implements Serializable {
    private Long alarmId;
    private String senderNickName;
    private Type type;
    private Long id;
    private String title;
    private LocalDateTime modifiedAt;
    private ReadingStatus readingStatus;

    public AlarmDto(Alarm alarm) {
        this.alarmId = alarm.getAlarmId();
        this.senderNickName = alarm.getSenderNickName();
        this.type = alarm.getType();
        this.id = alarm.getId();
        this.title = alarm.getTitle();
        this.modifiedAt = alarm.getModifiedAt();
        this.readingStatus = alarm.getReadingStatus();
    }

    public static AlarmDto convertMessageToDto(Alarm alarm) {
        return new AlarmDto(
                alarm.getAlarmId(),
                alarm.getSenderNickName(),
                alarm.getType(),
                alarm.getId(),
                alarm.getTitle(),
                alarm.getModifiedAt(),
                alarm.getReadingStatus()
        );
    }
}
