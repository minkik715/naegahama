package com.hanghae.naegahama.handler;

import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.alarm.AlarmResponseDto;
import com.hanghae.naegahama.handler.event.*;
import com.hanghae.naegahama.handler.ex.UserNotFoundException;
import com.hanghae.naegahama.repository.AlarmRepository;
import com.hanghae.naegahama.repository.CommentRepository;
import com.hanghae.naegahama.repository.UserRepository;
import com.hanghae.naegahama.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Slf4j
@RequiredArgsConstructor
public class EventHandler {

    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;



    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)

    public void answerLikeEvent(AnswerLikeEvent answerLikeEvent) {
        //answer에 라이크가 생기면 -> answer를 쓴 사람에게 5포인트룰 줘야하고, 알람이 날아가야 한다. 업적도 생기나?
        User receiver = answerLikeEvent.getReceiver();
        User sender = answerLikeEvent.getSender();
        Answer answer = answerLikeEvent.getAnswer();
        AlarmType alarmType = AlarmType.likeA;

        if (!receiver.getNickName().equals(sender.getNickName())) {
            answerAlarm(receiver,sender,answer,alarmType);
            givePointAndSendAlarm(receiver, 25, AlarmType.pointAL, answer);
        }

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void AnswerWriteEvent(AnswerWriteEvent AnswerWriteEvent){

        log.info("이벤터 시작");
        User receiver = AnswerWriteEvent.getReceiver();
        User sender = AnswerWriteEvent.getSender();
        Post post = AnswerWriteEvent.getPost();
        Answer answer = AnswerWriteEvent.getAnswer();
        AlarmType alarmType = AlarmType.answer;
        log.info("알람보내기");

        answerAlarm(answer.getUser(), answer.getUser(), answer, AlarmType.answerC);
        if (!receiver.getNickName().equals(sender.getNickName())) {
            postAlarm(receiver,sender,post,alarmType);
        }
        User findUser = userRepository.findById(sender.getId()).orElseThrow(
                () -> new UserNotFoundException("재균님을 찾을 수 없습니다. 도망쳐~")
        );
        if(findUser.getAchievement().getAchievement1() == 0) {
            findUser.getAchievement().setAchievement1(1);
            achieveAlarm(findUser,"내가HAMA");
        }
        log.info("굳굳굳");




        if(post.getAnswerList() !=null && post.getAnswerList().size() ==0){
            LocalDateTime deadLine = post.getDeadLine();
            long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), deadLine);
            log.info("잔여시간차이 = {}",minutes);
            if(minutes <60){
                givePointAndSendAlarm(sender, 50, AlarmType.pointA,answer);
            }
        }
        log.info("완료!");

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void StarGiveEvent(StarGiveEvent StarGiveEvent) {
        User receiver = StarGiveEvent.getReceiver();
        User sender = StarGiveEvent.getSender();
        Answer answer = StarGiveEvent.getAnswer();
        AlarmType alarmType = AlarmType.rated;
        Integer star = StarGiveEvent.getStar();

        if (!receiver.getNickName().equals(sender.getNickName())) {
            answerAlarm(receiver,sender,answer,alarmType);
        }
        User findUser = userRepository.findById(receiver.getId()).orElseThrow(
                () -> new UserNotFoundException("재균님을 찾을 수 없습니다. 도망쳐~")
        );

        // 1점을 받을 시 업적 1 획득
        if ( star.equals(1)) {
            if(findUser.getAchievement().getAchievement8() == 0) {
                findUser.getAchievement().setAchievement8(1);
                achieveAlarm(findUser,"더노력HAMA");
            }
        }
        // 5점을 받을 시 업적 2 획득
        else if (star.equals(5)) {
            if(findUser.getAchievement().getAchievement4() == 0) {
                findUser.getAchievement().setAchievement4(1);
                achieveAlarm(findUser,"축하HAMA");
            }
        }


        if(answer.getPost().getUser().getRole().equals(UserRoleEnum.ADMIN)){
            Integer addPoint = (star) * 150;
            String category = receiver.getCategory();
            if( category.equals( answer.getPost().getCategory())) {
                givePointAndSendAlarm(receiver, addPoint + 50, AlarmType.pointRD,answer);
            }
            else {
                givePointAndSendAlarm(receiver, addPoint,AlarmType.pointRD,answer);
            }
        }else{
            Integer addPoint = (star) * 100;
            String category = receiver.getCategory();
            if( category.equals( answer.getPost().getCategory())) {
                givePointAndSendAlarm(receiver, addPoint + 50,AlarmType.pointRD,answer);
            }
            else {
                givePointAndSendAlarm(receiver, addPoint,AlarmType.pointRD,answer);
            }
        }
        givePointAndSendAlarm(answer.getPost().getUser(), 50,AlarmType.pointR,answer);


        if(findUser.getAchievement().getAchievement2() == 0) {
            findUser.getAchievement().setAchievement2(1);
            achieveAlarm(findUser,"만족HAMA");
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void commentWriteEvent(CommentWriteEvent commentWriteEvent) {

        User receiver = commentWriteEvent.getReceiver();
        User sender = commentWriteEvent.getSender();
        Object object = commentWriteEvent.getObject();
        AlarmType alarmType = commentWriteEvent.getAlarmType();


        User findUser = userRepository.findById(sender.getId()).orElseThrow(
                () -> new UserNotFoundException("재균님을 찾을 수 없습니다. 도망쳐~")
        );
        if (!receiver.getNickName().equals(sender.getNickName())) {
            if(alarmType.equals(AlarmType.comment)) {
                answerAlarm(receiver, sender, (Answer) object, alarmType);
            }else if(alarmType.equals(AlarmType.child)){
                Comment comment = (Comment) object;
                Long parentCommentId = comment.getParentCommentId();
                Optional<Comment> parent = commentRepository.findById(parentCommentId);
                Comment comment1 = parent.get();

                commentAlarm(receiver,sender, comment1, alarmType);
            }
        }
        if(findUser.getAchievement().getAchievement6() == 0) {
            findUser.getAchievement().setAchievement6(1);
            achieveAlarm(findUser,"리액션HAMA");
        }

    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) //커밋완료 후 작업

    public void postLikeEvent(PostLikeEvent postLikeEvent) {
        //answer에 라이크가 생기면 -> answer를 쓴 사람에게 5포인트룰 줘야하고, 알람이 날아가야 한다. 업적도 생기나?
        User receiver = userRepository.findById(postLikeEvent.getReceiver().getId()).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        User sender = postLikeEvent.getSender();
        Post post = postLikeEvent.getPost();
        AlarmType alarmType = AlarmType.likeP;

        if (!receiver.getNickName().equals(sender.getNickName())) {
            postAlarm(receiver,sender,post,alarmType);
            givePointAndSendAlarm(receiver, 25,AlarmType.pointPL,post);
        }
        log.info("이벤트리스너순서");

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void postWriteEvent(PostWriteEvent postWriteEvent) {
        //answer에 라이크가 생기면 -> answer를 쓴 사람에게 5포인트룰 줘야하고, 알람이 날아가야 한다. 업적도 생기나?
        Post post = postWriteEvent.getPost();
        User findUser = userRepository.findById(post.getUser().getId()).orElseThrow(
                () -> new UserNotFoundException("재균님을 찾을 수 없습니다. 도망쳐~")
        );
        if(findUser.getAchievement().getAchievement3() == 0) {
            findUser.getAchievement().setAchievement3(1);
            achieveAlarm(findUser,"잘부탁HAMA");
        }

    }




    public void givePointAndSendAlarm(User user, int i, AlarmType alarmType, Object object) {
        log.info("에러는여기서1?");
        log.info("에러는여기서2?");
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new UserNotFoundException("유저를 찾을 수 없습니다")
        );
        findUser.setPoint(findUser.getPoint()+i);
        List<Alarm> alarmList = findUser.sendAlarm(i ,alarmType, object);


        log.info("user point = {}", findUser.getPoint());
        for (Alarm alarm : alarmList) {
            Alarm saveAlarm = alarmRepository.save(alarm);
            alarmService.alarmByMessage(new AlarmResponseDto(saveAlarm));
        }

        log.info("user point = {}", findUser.getPoint());

        log.info("user point = {}", findUser.getPoint());



    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void searchWordEvent(SearchWordEvent searchWordEvent) {
        //answer에 라이크가 생기면 -> answer를 쓴 사람에게 5포인트룰 줘야하고, 알람이 날아가야 한다. 업적도 생기나?
        User findUser = userRepository.findById(searchWordEvent.getUser().getId()).orElseThrow(
                () -> new UserNotFoundException("유저를 찾을 수 없습니다")
        );
        if(findUser.getAchievement().getAchievement7() == 0) {
            findUser.getAchievement().setAchievement7(1);
            achieveAlarm(findUser,"서치HAMA");
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void surveyEvent(SurveyEvent surveyEvent) {
        User findUser = userRepository.findById(surveyEvent.getUser().getId()).orElseThrow(
                () -> new UserNotFoundException("유저를 찾을 수 없습니다")
        );
        if(findUser.getAchievement().getAchievement5() == 0) {
            findUser.getAchievement().setAchievement5(1);
            achieveAlarm(findUser,"분석HAMA");
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void postClosedEvent( PostClosedEvent postClosedEvent) {
        //answer에 라이크가 생기면 -> answer를 쓴 사람에게 5포인트룰 줘야하고, 알람이 날아가야 한다. 업적도 생기나?
        User receiver = postClosedEvent.getReceiver();
        Post post = postClosedEvent.getPost();
        AlarmType alarmType = AlarmType.rate;

        postAlarm(receiver,receiver,post,alarmType);
    }





    private void commentAlarm(User receiver, User sender, Comment object, AlarmType alarmType) {
        Alarm save1 = alarmRepository.save(new Alarm(receiver, sender.getNickName(), alarmType, object.getId(), object.getContent()));
        alarmService.alarmByMessage(new AlarmResponseDto(save1));
    }

    private void postAlarm(User receiver, User sender, Post object, AlarmType alarmType) {
        Alarm save1 = alarmRepository.save(new Alarm(receiver, sender.getNickName(), alarmType, object.getId(), object.getTitle()));
        alarmService.alarmByMessage(new AlarmResponseDto(save1));
    }

    private void answerAlarm(User receiver, User sender, Answer object, AlarmType alarmType) {
        Alarm save1 = alarmRepository.save(new Alarm(receiver, sender.getNickName(), alarmType, object.getId(), object.getTitle()));
        alarmService.alarmByMessage(new AlarmResponseDto(save1));
    }

    private void achieveAlarm(User receiver, String achieveContent) {
        Alarm save1 = alarmRepository.save(new Alarm(receiver, null, AlarmType.achieve, receiver.getId(), achieveContent));
        alarmService.alarmByMessage(new AlarmResponseDto(save1));
    }




}