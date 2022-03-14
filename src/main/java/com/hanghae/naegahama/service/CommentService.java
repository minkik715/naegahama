
package com.hanghae.naegahama.service;

//import com.hanghae.naegahama.alarm.*;
import com.hanghae.naegahama.domain.Achievement;
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.comment.*;
import com.hanghae.naegahama.handler.ex.AnswerNotFoundException;
import com.hanghae.naegahama.handler.ex.CommentNotFoundException;
import com.hanghae.naegahama.repository.AchievementRepository;
import com.hanghae.naegahama.repository.AnswerRepository;
import com.hanghae.naegahama.repository.CommentRepository;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
//    private final AlarmService alarmService;
//    private final MessageRepository messageRepository;
    private final AchievementRepository achievementRepository;

    @Transactional
    public ResponseEntity<?> writeComment(Long answerId, CommentRequestDto commentRequestDto, User user) {
        Answer findAnswer = answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글이 존재하지 않습니다.")
        );
        String commentContent = commentRequestDto.getComment();
        Long parentCommentId = commentRequestDto.getParentCommentId();
        Comment comment = null;
        if(parentCommentId == null) {
            String timestamp = commentRequestDto.getTimestamp();
            if(timestamp !=null){
                String[] split = timestamp.split(":");

            }
            comment = new Comment(commentContent, findAnswer, user, timestamp);
            user.getCommentList().add(comment);
        }else{
            comment = new Comment(commentContent,parentCommentId, findAnswer, user);
            user.getCommentList().add(comment);
        }


        Comment save = commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(save,answerId);


        // 최초 평가시 업적 7 획득
        User achievementUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));
        Achievement achievement = achievementRepository.findByUser(achievementUser);
        achievement.AddAchievement(4);




//        Message message = new Message(findAnswer.getUser(),findAnswer.getTitle()+"에 댓글이 달렸습니다.");
//        Message save1 = messageRepository.save(message);
//        alarmService.alarmByMessage(new MessageDto(save1));
        return ResponseEntity.ok().body(commentResponseDto);



    }

    public ResponseEntity<?> modifyComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다.")
        );
        findComment.setComment(commentModifyRequestDto.getContent(),commentModifyRequestDto.getTimestamp());
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }


    public ResponseEntity<?> deleteComment(Long commentId) {
       commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다.")
        );
        commentRepository.deleteAll(commentRepository.findAllByParentCommentId(commentId));
        commentRepository.deleteById(commentId);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }


    @Transactional(readOnly = true)
    public ResponseEntity<?> getCommentList(Long answerId) {
        //글에 대한 댓글
        Answer findAnswer = answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글이 존재하지 않습니다.")
        );
        //답변글에 달린 모든 댓글
        List<Comment> commentList = findAnswer.getCommentList();

        // 날라갈 댓글 글 리스트
        List<CommentListResponseDto> parentCommentListResponseDtoList = new ArrayList<>();
        if(commentList == null){
            throw new CommentNotFoundException("해당 글에는 댓글이 존재하지 않습니다.");
        }
        for (Comment comment : commentList) {
            if(comment.getParentCommentId() == null) {
                CommentListResponseDto commentListResponseDto = new CommentListResponseDto(comment);
                parentCommentListResponseDtoList.add(commentListResponseDto);
            }
        }
        return ResponseEntity.ok().body(parentCommentListResponseDtoList);

    }

    public ResponseEntity<?> getKidsCommentList(Long commentId) {
        Comment parentComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글은 존재하지 않습니다.")
        );
        List<Comment> kidsCommentList = commentRepository.findAllByParentCommentId(commentId);
        List<KidsCommentListResponseDto> kidsCommentListResponseDtoList = new ArrayList<>();
        if(kidsCommentList == null){
            throw new CommentNotFoundException("해당 댓글에는 대댓글이 존재하지 않습니다.");
        }
        for (Comment comment : kidsCommentList) {
            KidsCommentListResponseDto kidsCommentListResponseDto = new KidsCommentListResponseDto(comment);
            kidsCommentListResponseDtoList.add(kidsCommentListResponseDto);

        }
        AllCommentResponseDto allCommentResponseDto = new AllCommentResponseDto(new CommentResponseDto(parentComment,parentComment.getAnswer().getId()),kidsCommentListResponseDtoList);
        return ResponseEntity.ok().body(allCommentResponseDto);
    }
}

