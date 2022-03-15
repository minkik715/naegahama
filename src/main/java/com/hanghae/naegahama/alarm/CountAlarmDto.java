package com.hanghae.naegahama.alarm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountAlarmDto {
    private Long alarmCount;

    public CountAlarmDto(Long alarmCount) {
        this.alarmCount = alarmCount;
    }
}
