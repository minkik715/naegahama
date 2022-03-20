package com.hanghae.naegahama.alarm;

import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlarmResponseDto {

    private Long alarmId;
    private String senderNickName;
    private AlarmType alarmType;
    private Long id;
    private String title;
    private String modifiedAt;
    private ReadingStatus readingStatus;
    private String timeSet;

    public AlarmResponseDto(Long alarmId, String senderNickName, AlarmType alarmType, Long id, String title,
                            LocalDateTime modifiedAt, ReadingStatus readingStatus, String timeSet) {
        this.alarmId = alarmId;
        this.senderNickName = senderNickName;
        this.alarmType = alarmType;
        this.id = id;
        this.title = title;
        this.modifiedAt = TimeHandler.setModifiedAtComment(modifiedAt);
        this.readingStatus = readingStatus;
        this.timeSet = timeSet;

    }
}

