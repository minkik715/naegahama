
package com.hanghae.naegahama.service;

import com.hanghae.naegahama.alarm.*;
import com.hanghae.naegahama.alarm.AlarmService;
import com.hanghae.naegahama.alarm.MessageDto;
import com.hanghae.naegahama.alarm.AlarmRepository;
import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.comment.*;
import com.hanghae.naegahama.handler.ex.AnswerNotFoundException;
import com.hanghae.naegahama.handler.ex.CommentNotFoundException;
import com.hanghae.naegahama.repository.AnswerRepository;
import com.hanghae.naegahama.repository.CommentRepository;
import com.hanghae.naegahama.repository.UserRepository;
import com.hanghae.naegahama.security.jwt.JwtDecoder;
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
    private final AlarmService alarmService;
    private final AlarmRepository alarmRepository;
    private final JwtDecoder jwtDecoder;


    @Transactional
    public ResponseEntity<?> writeComment(Long answerId, CommentRequestDto commentRequestDto, User user) {
        Answer findAnswer = answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글이 존재하지 않습니다.")
        );
        String commentContent = commentRequestDto.getComment();
        Long parentCommentId = commentRequestDto.getParentCommentId();
        Comment comment = null;
        if (parentCommentId == null) {
            String timestamp = commentRequestDto.getTimestamp();

            comment = new Comment(commentContent, findAnswer, user, timestamp);
            user.getCommentList().add(comment);
            //답변글에 댓글을 단 사람에게 주는 알람.(대댓글 미포함 하고싶음.)
            if (!findAnswer.getUser().equals(comment.getUser())) {
                Alarm alarm = new Alarm(findAnswer.getUser(), comment.getUser().getNickName(), Type.comment, findAnswer.getId(), findAnswer.getTitle());
                Alarm save1 = alarmRepository.save(alarm);
                alarmService.alarmByMessage(new MessageDto(save1));
            }
        } else {
            comment = new Comment(commentContent, parentCommentId, findAnswer, user);
            user.getCommentList().add(comment);

            //댓글에 대댓글을 단 사람에게 주는 알람.

                Comment findcomment = commentRepository.findById(parentCommentId).orElseThrow(
                        () -> new CommentNotFoundException("댓글 없습니다.")
                );
            if (!findcomment.getUser().equals(comment.getUser())) {
                Alarm alarm1 = new Alarm(findcomment.getUser(), comment.getUser().getNickName(), Type.child, parentCommentId, findcomment.getContent());
                Alarm save2 = alarmRepository.save(alarm1);
                alarmService.alarmByMessage(new MessageDto(save2));
            }
        }
        Comment save = commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(save, answerId);


        // 최초 평가시 업적 7 획득
        User achievementUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));
        achievementUser.getAchievement().setAchievement6(1);


        return ResponseEntity.ok().body(commentResponseDto);
    }


    public ResponseEntity<?> modifyComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다.")
        );
        findComment.setComment(commentModifyRequestDto.getContent(), commentModifyRequestDto.getTimestamp());
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
        if (commentList == null) {
            throw new CommentNotFoundException("해당 글에는 댓글이 존재하지 않습니다.");
        }
        for (Comment comment : commentList) {

            if(comment.getParentCommentId() == null) {
                CommentListResponseDto commentListResponseDto = new CommentListResponseDto(comment,comment.getUser());
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
        if (kidsCommentList == null) {
            throw new CommentNotFoundException("해당 댓글에는 대댓글이 존재하지 않습니다.");
        }
        for (Comment comment : kidsCommentList) {
            KidsCommentListResponseDto kidsCommentListResponseDto = new KidsCommentListResponseDto(comment, comment.getUser());
            kidsCommentListResponseDtoList.add(kidsCommentListResponseDto);

        }
        AllCommentResponseDto allCommentResponseDto = new AllCommentResponseDto(new CommentResponseDto(parentComment, parentComment.getAnswer().getId()), kidsCommentListResponseDtoList);
        return ResponseEntity.ok().body(allCommentResponseDto);
    }
}

