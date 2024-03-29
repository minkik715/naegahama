package com.hanghae.naegahama.handler;

import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.alarm.AlarmResponseDto;
import com.hanghae.naegahama.event.*;
import com.hanghae.naegahama.repository.alarmrepository.AlarmRepository;
import com.hanghae.naegahama.repository.commentrepository.CommentRepository;
import com.hanghae.naegahama.repository.userrepository.UserRepository;
import com.hanghae.naegahama.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import com.hanghae.naegahama.util.ComfortMethods;
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
    public void WriteUserPageComment(MyPageCommentEvent myPageCommentEvent) {
        User receiver = myPageCommentEvent.getReceiver();
        User sender = myPageCommentEvent.getSender();
        UserComment userComment = myPageCommentEvent.getUserComment();
        AlarmType alarmType = AlarmType.commentMP;

        if (!receiver.getNickName().equals(sender.getNickName())) {
            myPageUserAlarm(receiver,sender,userComment,alarmType);
        }

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void AnswerWriteEvent(AnswerWriteEvent AnswerWriteEvent){

        User receiver = AnswerWriteEvent.getReceiver();
        User sender = AnswerWriteEvent.getSender();
        Post post = AnswerWriteEvent.getPost();
        Answer answer = AnswerWriteEvent.getAnswer();
        AlarmType alarmType = AlarmType.answer;

        answerAlarm(answer.getUser(), answer.getUser(), answer, AlarmType.answerC);
        if (!receiver.getNickName().equals(sender.getNickName())) {
            postAlarm(receiver,sender,post,alarmType);
        }
        User findUser = ComfortMethods.getUser(sender.getId());
        if(findUser.getAchievement().getAchievement1() == 0) {
            findUser.getAchievement().setAchievement1(1);
            achieveAlarm(findUser,"내가HAMA");
        }




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
        User findUser = ComfortMethods.getUser(receiver.getId());

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



        if (!receiver.getNickName().equals(sender.getNickName())) {
            if(alarmType.equals(AlarmType.comment)) {
                answerAlarm(receiver, sender, (Answer) object, alarmType);
            }else if(alarmType.equals(AlarmType.child)){
                Comment comment = (Comment) object;
                Long parentCommentId = comment.getParentComment().getId();
                Optional<Comment> parent = commentRepository.findById(parentCommentId);
                Comment comment1 = parent.get();

                commentAlarm(receiver,sender, comment1, alarmType);
            }
        }
        User findUser = ComfortMethods.getUser(sender.getId());
        if(findUser.getAchievement().getAchievement6() == 0) {
            findUser.getAchievement().setAchievement6(1);
            achieveAlarm(findUser,"리액션HAMA");
        }

    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) //커밋완료 후 작업

    public void postLikeEvent(PostLikeEvent postLikeEvent) {
        User receiver = postLikeEvent.getReceiver();
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
        Post post = postWriteEvent.getPost();
        User findUser = ComfortMethods.getUser(post.getUser().getId());
        if(findUser.getAchievement().getAchievement3() == 0) {
            findUser.getAchievement().setAchievement3(1);
            achieveAlarm(findUser,"잘부탁HAMA");
        }

    }




    public void givePointAndSendAlarm(User user, int i, AlarmType alarmType, Object object) {
        User findUser = ComfortMethods.getUser(user.getId());
        findUser.setPoint(findUser.getPoint()+i);
        List<Alarm> alarmList = findUser.sendAlarm(i ,alarmType, object);
        for (Alarm alarm : alarmList) {
            Alarm saveAlarm = alarmRepository.save(alarm);
            alarmService.alarmByMessage(new AlarmResponseDto(saveAlarm));
        }

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void searchWordEvent(SearchWordEvent searchWordEvent) {
        User findUser = ComfortMethods.getUser(searchWordEvent.getUser().getId());
        if(findUser.getAchievement().getAchievement7() == 0) {
            findUser.getAchievement().setAchievement7(1);
            achieveAlarm(findUser,"서치HAMA");
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void surveyEvent(SurveyEvent surveyEvent) {
        User findUser = ComfortMethods.getUser(surveyEvent.getUser().getId());
        findUser.setHippoName(surveyEvent.getHippoName());
        if(findUser.getAchievement().getAchievement5() == 0) {
            findUser.getAchievement().setAchievement5(1);
            achieveAlarm(findUser,"분석HAMA");
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void postClosedEvent( PostClosedEvent postClosedEvent) {
        User receiver = postClosedEvent.getReceiver();
        Post post = postClosedEvent.getPost();
        AlarmType alarmType = AlarmType.rate;
        log.info("2번?");

        postAlarm(receiver,receiver,post,alarmType);
    }





    private void commentAlarm(User receiver, User sender, Comment object, AlarmType alarmType) {
        Alarm save1 = alarmRepository.save(new Alarm(receiver, sender.getNickName(), alarmType, object.getId(), object.getContent()));
        alarmService.alarmByMessage(new AlarmResponseDto(save1));
    }

    private void postAlarm(User receiver, User sender, Post object, AlarmType alarmType) {
        Alarm save1 = alarmRepository.save(new Alarm(receiver, sender.getNickName(), alarmType, object.getId(), object.getTitle()));
        log.info("2번");
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

    private void myPageUserAlarm(User receiver, User sender, UserComment userComment, AlarmType alarmType){
        Alarm alarm = alarmRepository.save(new Alarm(receiver,sender.getNickName(),alarmType,userComment.getId(), userComment.getContent()));
        alarmService.alarmByMessage(new AlarmResponseDto(alarm));
    }



}
