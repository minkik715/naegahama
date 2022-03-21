package com.hanghae.naegahama.service;

import com.hanghae.naegahama.alarm.*;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.answer.*;
import com.hanghae.naegahama.dto.event.AlarmEventListener;
import com.hanghae.naegahama.dto.event.AnswerVideoEncoding;
import com.hanghae.naegahama.handler.ex.UserNotFoundException;
import com.hanghae.naegahama.repository.*;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AnswerService
{
    private final AnswerRepository answerRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AnswerLikeRepository answerLikeRepository;
    private final AnswerFileRepository answerFileRepository;
    private final UserRepository userRepository;
    private final AnswerVideoRepository answerVideoRepository;
    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;
    private final ApplicationEventPublisher applicationEventPublisher;

    // 답변글 작성

    public ResponseEntity<?> answerWrite(AnswerPostRequestDto answerPostRequestDto, Long postId, User user)
    {
        Post post = postRepository.findPostById(postId);

        if(post.getStatus().equals("false"))
        {
            return ResponseEntity.badRequest().body("마감이 된 글에는 답변을 작성할 수 없습니다.");
        }

        //저장된 Answer을 꺼내와서
        Answer saveAnwser = answerRepository.save(new Answer(answerPostRequestDto,post,user));

        for ( String url : answerPostRequestDto.getFile())
        {
            // 이미지 파일 url로 answerFile 객체 생성
            AnswerFile fileUrl = new AnswerFile(url);

            // answerFile saveanswer를 연관관계 설정
            fileUrl.setAnswer(saveAnwser);

            // 이미지 파일 url 1개에 해당되는 answerFile을 DB에 저장
            AnswerFile saveFile = answerFileRepository.save(fileUrl);

            // 저장된 answerFile을 저장된 answer에 한개씩 추가함
            saveAnwser.getFileList().add(saveFile);
        }

        AnswerVideo videoUrl = new AnswerVideo(answerPostRequestDto.getVideo());
        videoUrl.setAnswer(saveAnwser);

        //빠뜨리신 재균님?
        answerVideoRepository.save(videoUrl);
        // 최초 요청글 작성시 업적 5 획득
        User achievementUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));

        if(post.getAnswerList() !=null && post.getAnswerList().size() ==0){
            LocalDateTime deadLine = post.getDeadLine();
            long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), deadLine);
            log.info("잔여시간차이 = {}",minutes);
            if(minutes <60){
                achievementUser.addPoint(50);
            }
        }



        achievementUser.getAchievement().setAchievement1(1);

        applicationEventPublisher.publishEvent(new AlarmEventListener(post.getUser(), user,post,AlarmType.answer));
        applicationEventPublisher.publishEvent(new AnswerVideoEncoding(saveAnwser.getId()));



        return ResponseEntity.ok().body(saveAnwser.getId());
    }


    // 요청 글에 달린 answerList 조회
    public List<AnswerGetResponseDto> answerList(Long postId)
    {
        List<Answer> answerList = answerRepository.findAllByPostIdOrderByCreatedAt(postId);
        List<AnswerGetResponseDto> answerGetResponseDtoList = new ArrayList<>();

        for ( Answer answer : answerList)
        {
            Long commentCount = commentRepository.countByAnswer(answer);
            Long likeCount = answerLikeRepository.countByAnswer(answer);
            int imageCount = answer.getFileList().size();
            AnswerGetResponseDto answerGetResponseDto = new AnswerGetResponseDto(answer,commentCount,likeCount,imageCount);
            answerGetResponseDtoList.add(answerGetResponseDto);
        }

        return answerGetResponseDtoList;

    }


    // 응답글 수정
    public ResponseEntity<?> answerUpdate(Long answerId, UserDetailsImpl userDetails, AnswerPutRequestDto answerPutRequestDto )
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));
        Post post = postRepository.findPostById(answer.getPost().getId());
        if(post.getStatus().equals("false")){
            return ResponseEntity.badRequest().body("마감이 된 글에는 답변을 수정할 수 없습니다.");
        }

        answer.Update(answerPutRequestDto);

        // 기존에 있던 포스트파일 제거
        answerFileRepository.deleteByAnswer(answer);
        answerVideoRepository.deleteByAnswer(answer);

        // 새로운 이미지 파일 url 배열로 for 반복문을 실행
        for (String url : answerPutRequestDto.getFile()) {
            // 이미지 파일 url로 postFile 객체 생성
            AnswerFile fileUrl = new AnswerFile(url);

            // postFile에 savePost를 연관관계 설정
            fileUrl.setAnswer(answer);

            // 이미지 파일 url 1개에 해당되는 postFile을 DB에 저장
            AnswerFile saveFile = answerFileRepository.save(fileUrl);

            // 저장된 postFile을 저장된 post에 한개씩 추가함
            answer.getFileList().add(saveFile);
        }

        AnswerVideo videoUrl = new AnswerVideo(answerPutRequestDto.getVideo());
        videoUrl.setAnswer(answer);
        answerVideoRepository.save(videoUrl);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public ResponseEntity<?> answerDelete(Long answerId, UserDetailsImpl userDetails)
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

     /*   answerLikeRepository.deleteByAnswer(answer);
        answerFileRepository.deleteByAnswer(answer);
        answerVideoRepository.deleteByAnswer(answer);
        commentRepository.deleteByAnswer(answer);*/
        answerRepository.deleteById(answerId);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }


    // 답변글 상세 조회
    public AnswerDetailGetResponseDto answerDetail(Long answerId)
    {
        Answer answer =  answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        Long likeCount = answerLikeRepository.countByAnswer(answer);
        Long commentCount = commentRepository.countByAnswer(answer);

        List<AnswerLike> likeList = answer.getLikeList();
        List<Long> likeUserList = new ArrayList<>();

        List<AnswerFile> findAnswerFileList = answerFileRepository.findAllByAnswerOrderByCreatedAt(answer);

        List<String> fileList = new ArrayList<>();
        for (AnswerFile answerFile : findAnswerFileList) {
            fileList.add(answerFile.getUrl());
        }
        for ( AnswerLike likeUser : likeList)
        {
            likeUserList.add(likeUser.getUser().getId());
        }

//        AnswerVideo answerVideo = answerVideoRepository.findByAnswer(answer).orElseThrow(
//                () -> new IllegalArgumentException("비디오가 존재하지 않습니다."));


        AnswerDetailGetResponseDto answerDetailGetResponseDto = new AnswerDetailGetResponseDto(answer,likeCount,commentCount,likeUserList,fileList, answer.getPost().getCategory());

        return answerDetailGetResponseDto;
    }

    @Transactional
    public ResponseEntity<?> answerStar(Long answerId, UserDetailsImpl userDetails, StarPostRequestDto starPostRequestDto)
    {
        User requestWriter = userDetails.getUser();

        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new IllegalArgumentException("해당 답글은 존재하지 않습니다."));

        if( answer.getStar() != 0 )
        {
            throw new IllegalArgumentException("이미 평가한 답글입니다.");
        }
        User user = userRepository.findById(requestWriter.getId()).orElseThrow(
                () -> new UserNotFoundException("해당 유저는 존재하지 않습니다.")
        );

        answer.Star(starPostRequestDto);
        User answerWriter = answer.getUser();

        // 1점을 받을 시 업적 1 획득
        if ( starPostRequestDto.getStar() == 1)
        {
            user.getAchievement().setAchievement8(1);
        }
        // 5점을 받을 시 업적 2 획득
        else if( starPostRequestDto.getStar() == 5)
        {
            user.getAchievement().setAchievement4(1);
        }

        // 최초 평가시 업적 7 획득
        User achievementUser = userRepository.findById(requestWriter.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));
        achievementUser.getAchievement().setAchievement2(1);

        Integer addPoint = (starPostRequestDto.getStar()) * 100;
        String category = answerWriter.getCategory();
        if( category.equals( answer.getPost().getCategory()))
        {
            answerWriter.addPoint( addPoint + 50 );
        }
        else
        {
            answerWriter.addPoint( addPoint );
        }
        applicationEventPublisher.publishEvent(new AlarmEventListener(answerWriter,achievementUser,answer,AlarmType.rated));
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

}
