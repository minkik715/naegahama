package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.answer.*;
import com.hanghae.naegahama.handler.ex.*;
import com.hanghae.naegahama.repository.*;
import com.hanghae.naegahama.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final AchievementRepository achievementRepository;


    // 답변글 작성
    @Transactional
    public ResponseEntity<?> answerWrite(AnswerRequestDto answerRequestDto, Long postId, UserDetailsImpl userDetails)
    {
        User user = GetUser(userDetails);
        Post post = postRepository.findPostById(postId);

        if(post.getStatus().equals("closed"))
        {
            throw new AnswerWritePostClosedException("닫힌 글입니다.");
        }

        Answer anwser = answerRepository.save(new Answer(answerRequestDto,post,user));
        AnswerFileSave(anwser,answerRequestDto);

        AnswerWritingExtraPoint(user,post);

        Achievement achievement = achievementRepository.findByUser(user);
        achievement.AddAchievement(8);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }


    // 요청 글에 달린 answerList 조회
    public List<AnswerGetResponseDto> answerList(Long postId)
    {
        List<Answer> answerList = answerRepository.findAllByPostIdOrderByCreatedAt(postId);
        List<AnswerGetResponseDto> answerGetResponseDtoList = new ArrayList<>();

        for ( Answer answer : answerList)
        {
            int commentCount = commentRepository.countByAnswer(answer);
            int likeCount = answerLikeRepository.countByAnswer(answer);
            int imageCount = answer.getFileList().size();
            AnswerGetResponseDto answerGetResponseDto = new AnswerGetResponseDto(answer,commentCount,likeCount,imageCount);
            answerGetResponseDtoList.add(answerGetResponseDto);
        }

        return answerGetResponseDtoList;
    }


    // 응답글 수정
    public ResponseEntity<?> answerUpdate(Long answerId, UserDetailsImpl userDetails, AnswerRequestDto answerRequestDto )
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글은 존재하지 않습니다."));
        Post post = postRepository.findPostById(answer.getPost().getId());

        if(userDetails.getUser().getId().equals(answer.getUser().getId()))
        {
            throw new UserNotFoundException("로그인한 유저가 작성자가 아닙니다.");
        }

        if(post.getStatus().equals("closed"))
        {
            throw new AnswerWritePostClosedException("마감이 된 글에는 답변을 수정할 수 없습니다.");
        }

        answer.Update(answerRequestDto);

        answerFileRepository.deleteByAnswer(answer);
        answerVideoRepository.deleteByAnswer(answer);

        AnswerFileSave(answer,answerRequestDto);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public ResponseEntity<?> answerDelete(Long answerId, UserDetailsImpl userDetails)
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글은 존재하지 않습니다."));

        answerLikeRepository.deleteByAnswer(answer);
        answerFileRepository.deleteByAnswer(answer);
        answerVideoRepository.deleteByAnswer(answer);
        commentRepository.deleteByAnswer(answer);
        answerRepository.deleteById(answerId);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }


    // 답변글 상세 조회
    public AnswerDetailGetResponseDto answerDetail(Long answerId)
    {
        Answer answer =  answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글은 존재하지 않습니다."));

        int likeCount = answerLikeRepository.countByAnswer(answer);
        int commentCount = commentRepository.countByAnswer(answer);

        List<String> fileList = new ArrayList<>();
        List<AnswerFile> findAnswerFileList = answerFileRepository.findAllByAnswerOrderByCreatedAt(answer);
        for (AnswerFile answerFile : findAnswerFileList) {
            fileList.add(answerFile.getUrl());
        }

        List<Long> likeUserList = new ArrayList<>();
        List<AnswerLike> likeList = answer.getLikeList();
        for ( AnswerLike likeUser : likeList)
        {
            likeUserList.add(likeUser.getUser().getId());
        }

        return new AnswerDetailGetResponseDto(answer,likeCount,commentCount,likeUserList,fileList );
    }

    // 작서자가
    public ResponseEntity<?> answerStar(Long answerId, UserDetailsImpl userDetails, StarPostRequestDto starPostRequestDto)
    {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글은 존재하지 않습니다."));
        User requestWriter = GetUser(userDetails);
        User answerWriter = answer.getUser();

        Achievement answerWriterAchievement = achievementRepository.findByUser(answerWriter);
        Achievement requestWriterAchievement = achievementRepository.findByUser(requestWriter);
        requestWriterAchievement.AddAchievement(7);

        if( answer.getStar() != 0 )
        {
            throw new AnswerStarExistException("이미 평가한 답글입니다.");
        }

        answer.Star(starPostRequestDto);
        if ( starPostRequestDto.getStar() == 1)
        {
            answerWriterAchievement.AddAchievement(1);
        }
        else if( starPostRequestDto.getStar() == 5)
        {
            answerWriterAchievement.AddAchievement(2);
        }
        requestWriterAchievement.AddAchievement(7);

        int addPoint = (starPostRequestDto.getStar()) * 100;

        String category =  answer.getPost().getCategory();

        if( category.equals( answerWriter.getCategory()))
        {
            answerWriter.addPoint( addPoint + 50 );
        }
        else if ( answerRepository.countByUserAndPost_CategoryAndStarGreaterThanEqual(answerWriter, category, 4) >= 5 )
        {
            answerWriter.addPoint( addPoint + 50 );
        }
        else
        {
            answerWriter.addPoint( addPoint );
        }

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    // @Transactional을 사용하기 위해 userRepository에서 불러온 유저를 반환
    private User GetUser(UserDetailsImpl userDetails)
    {
        return userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new LoginUserNotFoundException("유저를 찾을 수 없습니다."));
    }

    private void AnswerFileSave(Answer answer, AnswerRequestDto answerRequestDto)
    {
        for ( String url : answerRequestDto.getFile())
        {
            AnswerFile saveFile = answerFileRepository.save(new AnswerFile(url, answer));
            answer.getFileList().add(saveFile);
        }
        answerVideoRepository.save(new AnswerVideo(answerRequestDto.getVideo(), answer));
    }

    private void AnswerWritingExtraPoint(User user, Post post)
    {
        if(post.getAnswerList() !=null && post.getAnswerList().size() == 0)
        {
            LocalDateTime deadLine = post.getDeadLine();
            long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), deadLine);

            log.info("잔여시간차이 = {}",minutes);
            if(minutes <60)
            {
                user.addPoint(50);
            }
        }
    }

}
