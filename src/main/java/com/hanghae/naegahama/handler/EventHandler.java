package com.hanghae.naegahama.handler;

import com.hanghae.naegahama.alarm.*;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.event.*;
import com.hanghae.naegahama.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventHandler {

    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;

    private final PostRepository postRepository;

/*

  if (!receiver.getNickName().equals(sender.getNickName())) {

            if (alarmType.equals(AlarmType.rated) || alarmType.equals(AlarmType.likeA) || alarmType.equals(AlarmType.comment)) {
                answerAlarm(receiver, sender, (Answer) object, alarmType);
            } else if (alarmType.equals(AlarmType.likeP) || alarmType.equals(AlarmType.answer) || alarmType.equals(AlarmType.rate)) {
                postAlarm(receiver, sender, (Post) object, alarmType);
            } else if (alarmType.equals(AlarmType.child)) {
                commentAlarm(receiver, sender, (Comment) object, alarmType);
            }
        }
        *

        */
    @TransactionalEventListener
    public void answerLikeEvent(AnswerLikeEvent answerLikeEvent) {
        //answer에 라이크가 생기면 -> answer를 쓴 사람에게 5포인트룰 줘야하고, 알람이 날아가야 한다. 업적도 생기나?
        User receiver = answerLikeEvent.getReceiver();
        User sender = answerLikeEvent.getSender();
        Answer answer = answerLikeEvent.getAnswer();
        AlarmType alarmType = AlarmType.likeA;

        if (!receiver.getNickName().equals(sender.getNickName())) {
            answerAlarm(receiver,sender,answer,alarmType);
            givePointAndSendAlarm(receiver, 5);
        }

    }

    @TransactionalEventListener
    public void AnswerWriteEvent(AnswerWriteEvent AnswerWriteEvent){
        User receiver = AnswerWriteEvent.getReceiver();
        User sender = AnswerWriteEvent.getSender();
        Post post = AnswerWriteEvent.getPost();
        AlarmType alarmType = AlarmType.answer;

        if (!receiver.getNickName().equals(sender.getNickName())) {
            postAlarm(receiver,sender,post,alarmType);
        }

        sender.getAchievement().setAchievement1(1);


        if(post.getAnswerList() !=null && post.getAnswerList().size() ==0){
            LocalDateTime deadLine = post.getDeadLine();
            long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), deadLine);
            log.info("잔여시간차이 = {}",minutes);
            if(minutes <60){
                givePointAndSendAlarm(sender, 50);
            }
        }
    }

    @TransactionalEventListener
    public void StarGiveEvent(StarGiveEvent StarGiveEvent) {
        User receiver = StarGiveEvent.getReceiver();
        User sender = StarGiveEvent.getSender();
        Answer answer = StarGiveEvent.getAnswer();
        AlarmType alarmType = AlarmType.rated;
        Integer star = StarGiveEvent.getStar();

        if (!receiver.getNickName().equals(sender.getNickName())) {
            answerAlarm(receiver,sender,answer,alarmType);
        }

        // 1점을 받을 시 업적 1 획득
        if ( star.equals(1)) {
            receiver.getAchievement().setAchievement8(1);
        }
        // 5점을 받을 시 업적 2 획득
        else if (star.equals(5)) {
            receiver.getAchievement().setAchievement4(1);
        }

        // 최초 평가시 업적 7 획득
        Integer addPoint = (star) * 100;
        String category = receiver.getCategory();
        if( category.equals( answer.getPost().getCategory())) {
            givePointAndSendAlarm(receiver, addPoint + 50);
        }
        else {
            givePointAndSendAlarm(receiver, addPoint);
        }
        if(answer.getPost().getUser().getRole().equals(UserRoleEnum.ADMIN)){
            givePointAndSendAlarm(receiver, 200);

        }

        sender.getAchievement().setAchievement2(1);
    }

    @TransactionalEventListener
    public void commentWriteEvent(CommentWriteEvent commentWriteEvent) {

        User receiver = commentWriteEvent.getReceiver();
        User sender = commentWriteEvent.getSender();
        Object object = commentWriteEvent.getObject();
        AlarmType alarmType = commentWriteEvent.getAlarmType();

        if (!receiver.getNickName().equals(sender.getNickName())) {
            if(alarmType.equals(AlarmType.comment)) {
                answerAlarm(receiver, sender, (Answer) object, alarmType);
            }else if(alarmType.equals(AlarmType.child)){
                commentAlarm(receiver,sender, (Comment) object, alarmType);
            }
        }
        sender.getAchievement().setAchievement6(1);

    }

    @TransactionalEventListener
    public void postLikeEvent(PostLikeEvent postLikeEvent) {
        //answer에 라이크가 생기면 -> answer를 쓴 사람에게 5포인트룰 줘야하고, 알람이 날아가야 한다. 업적도 생기나?
        User receiver = postLikeEvent.getReceiver();
        User sender = postLikeEvent.getSender();
        Post post = postLikeEvent.getPost();
        AlarmType alarmType = AlarmType.likeP;

        if (!receiver.getNickName().equals(sender.getNickName())) {
            postAlarm(receiver,sender,post,alarmType);
            givePointAndSendAlarm(receiver, 5);
        }

    }

    @TransactionalEventListener
    public void postWriteEvent(PostWriteEvent postWriteEvent) {
        //answer에 라이크가 생기면 -> answer를 쓴 사람에게 5포인트룰 줘야하고, 알람이 날아가야 한다. 업적도 생기나?
        Post post = postWriteEvent.getPost();
        post.getUser().getAchievement().setAchievement3(1);
        Long postCount = postRepository.countByUser(post.getUser());
        if (postCount == 3 || postCount == 6) {
            givePointAndSendAlarm(post.getUser(), 50);

        }

    }

    private void givePointAndSendAlarm(User user, int i) {
        List<Alarm> alarmList = user.addPoint(i);
        for (Alarm alarm : alarmList) {
            alarmService.alarmByMessage(new MessageDto(alarm));
        }
    }

    @TransactionalEventListener
    public void searchWordEvent(SearchWordEvent searchWordEvent) {
        //answer에 라이크가 생기면 -> answer를 쓴 사람에게 5포인트룰 줘야하고, 알람이 날아가야 한다. 업적도 생기나?
        searchWordEvent.getUser().getAchievement().setAchievement7(1);
    }

    @TransactionalEventListener
    public void surveyEvent(SurveyEvent surveyEvent) {
        //answer에 라이크가 생기면 -> answer를 쓴 사람에게 5포인트룰 줘야하고, 알람이 날아가야 한다. 업적도 생기나?
        surveyEvent.getUser().getAchievement().setAchievement5(1);
    }

    @TransactionalEventListener
    public void postClosedEvent( PostClosedEvent postClosedEvent) {
        //answer에 라이크가 생기면 -> answer를 쓴 사람에게 5포인트룰 줘야하고, 알람이 날아가야 한다. 업적도 생기나?
        User receiver = postClosedEvent.getReceiver();
        Post post = postClosedEvent.getPost();
        AlarmType alarmType = AlarmType.rate;

        postAlarm(receiver,receiver,post,alarmType);
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
