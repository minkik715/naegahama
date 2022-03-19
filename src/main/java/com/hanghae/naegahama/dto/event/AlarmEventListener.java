package com.hanghae.naegahama.dto.event;

import com.hanghae.naegahama.alarm.AlarmType;
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class AlarmEventListener {
    User receiver;
    User sender;

    Object object;

    AlarmType alarmType;

    public AlarmEventListener(User receiver, User sender,Object object,AlarmType alarmType) {
        this.receiver = receiver;
        this.sender = sender;
        this.object = object;
        this.alarmType = alarmType;
    }
}
