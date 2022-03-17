package com.hanghae.naegahama.alarm;

import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlarmResponseDto {

    private Long alarmId;
    private String senderNickName;
    private Type type;
    private Long id;
    private String title;
    private LocalDateTime modifiedAt;
    private ReadingStatus readingStatus;
    private String timeSet;

    public AlarmResponseDto( Long alarmId, String senderNickName, Type type, Long id, String title,
                             LocalDateTime modifiedAt, ReadingStatus readingStatus, String timeSet) {
        this.alarmId = alarmId;
        this.senderNickName = senderNickName;
        this.type = type;
        this.id = id;
        this.title = title;
        this.modifiedAt = modifiedAt;
        this.readingStatus = readingStatus;
        this.timeSet = timeSet;

    }
}

