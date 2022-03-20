package com.hanghae.naegahama.handler.alarmeventhandler;

import com.hanghae.naegahama.alarm.*;
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.event.AlarmEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AlarmHandler {

    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;

    @EventListener
    public void answerLikeListener(AlarmEventListener alarmEventListener) {
        User receiver = alarmEventListener.getReceiver();
        User sender = alarmEventListener.getSender();
        Object object = alarmEventListener.getObject();
        AlarmType alarmType = alarmEventListener.getAlarmType();

        if (!receiver.getNickName().equals(sender.getNickName()))
        {
            if (alarmType.equals(AlarmType.rated) || alarmType.equals(AlarmType.likeA) || alarmType.equals(AlarmType.comment)) {
                answerAlarm(receiver, sender, (Answer) object, alarmType);
            } else if (alarmType.equals(AlarmType.likeP) || alarmType.equals(AlarmType.answer) || alarmType.equals(AlarmType.rate)) {
                postAlarm(receiver, sender, (Post) object, alarmType);
            } else if (alarmType.equals(AlarmType.child)) {
                commentAlarm(receiver, sender, (Comment) object, alarmType);
            }
        }
    }

    private void commentAlarm(User receiver, User sender, Comment object, AlarmType alarmType) {
        Alarm save1 = alarmRepository.save(new Alarm(receiver, sender.getNickName(), alarmType, object.getId(), object.getContent()));
        alarmService.alarmByMessage(new MessageDto(save1));
    }

    private void postAlarm(User receiver, User sender, Post object, AlarmType alarmType) {
        Alarm save1 = alarmRepository.save(new Alarm(receiver, sender.getNickName(), alarmType, object.getId(), object.getTitle()));
        alarmService.alarmByMessage(new MessageDto(save1));
    }

    private void answerAlarm(User receiver, User sender, Answer object, AlarmType alarmType) {
        Alarm save1 = alarmRepository.save(new Alarm(receiver, sender.getNickName(), alarmType, object.getId(), object.getTitle()));
        alarmService.alarmByMessage(new MessageDto(save1));
    }


}
