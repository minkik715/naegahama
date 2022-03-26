
package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.comment.*;
import com.hanghae.naegahama.handler.event.CommentWriteEvent;
import com.hanghae.naegahama.ex.AnswerNotFoundException;
import com.hanghae.naegahama.ex.CommentNotFoundException;
import com.hanghae.naegahama.ex.UserNotFoundException;
import com.hanghae.naegahama.repository.AnswerRepository;
import com.hanghae.naegahama.repository.CommentRepository;
import com.hanghae.naegahama.repository.UserCommentRepository;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserCommentRepository userCommentRepository;



    @Transactional
    public ResponseEntity<?> writeComment(Long answerId, CommentRequestDto commentRequestDto, User user) {
        Answer findAnswer = answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글이 존재하지 않습니다.")
        );
        String commentContent = commentRequestDto.getComment();
        Long parentCommentId = commentRequestDto.getParentCommentId();
        Comment comment = null;
        User findUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));
        if (parentCommentId == null) {
            String timestamp = commentRequestDto.getTimestamp();

            comment = new Comment(commentContent, findAnswer, user, timestamp);
            user.getCommentList().add(comment);
            //답변글에 댓글을 단 사람에게 주는 알람.(대댓글 미포함 하고싶음.)
            applicationEventPublisher.publishEvent(new CommentWriteEvent(findAnswer.getUser(), findUser,findAnswer, AlarmType.comment));

        } else {
            comment = new Comment(commentContent, parentCommentId, findAnswer, user);
            user.getCommentList().add(comment);

            //댓글에 대댓글을 단 사람에게 주는 알람.

                Comment findcomment = commentRepository.findById(parentCommentId).orElseThrow(
                        () -> new CommentNotFoundException("댓글 없습니다.")
                );
            applicationEventPublisher.publishEvent(new CommentWriteEvent(findcomment.getUser(), findUser, comment, AlarmType.child));

        }
        Comment save = commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(save, answerId);


        // 최초 평가시 업적 7 획득




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
                Long childCnt = commentRepository.countByParentCommentId(comment.getId());
                CommentListResponseDto commentListResponseDto = new CommentListResponseDto(comment,comment.getUser(),childCnt);
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


    public ResponseEntity<?> writeUserPageComment(Long userId, CommentUserPageRequestDto commentUserPageRequestDto, User user)
    {

        User pageUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("해당 유저가 존재하지 않습니다.")
        );
        String commentContent = commentUserPageRequestDto.getComment();
        Long parentCommentId = commentUserPageRequestDto.getParentCommentId();
        UserComment comment = null;
        User writer = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));
        if (parentCommentId == null)
        {
            comment = userCommentRepository.save(new UserComment(commentContent, pageUser, user));
//            user.getMycomment().add(comment);
            //답변글에 댓글을 단 사람에게 주는 알람.(대댓글 미포함 하고싶음.)
//            applicationEventPublisher.publishEvent(new CommentWriteEvent(findAnswer.getUser(), findUser,findAnswer, AlarmType.comment));

        } else {
            comment = userCommentRepository.save(new UserComment(commentContent, parentCommentId, pageUser, user));
//            user.getMycomment().add(comment);

            //댓글에 대댓글을 단 사람에게 주는 알람.

            UserComment findcomment = userCommentRepository.findById(parentCommentId).orElseThrow(
                    () -> new CommentNotFoundException("댓글 없습니다.")
            );
//            applicationEventPublisher.publishEvent(new CommentWriteEvent(findcomment.getUser(), findUser, comment, AlarmType.child));

        }
        UserComment save = userCommentRepository.save(comment);
        UserCommentResponseDto commentResponseDto = new UserCommentResponseDto(save, pageUser.getId());

        // 최초 평가시 업적 7 획득

        return ResponseEntity.ok().body(commentResponseDto);

    }

    public ResponseEntity<?> getUserPageComment(Long userId)
    {

        User pageUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("해당 유저가 존재하지 않습니다.") );
        //답변글에 달린 모든 댓글

        List<UserComment> commentList = pageUser.getMycomment();

        // 날라갈 댓글 글 리스트
        List<UserCommentListResponseDto> parentCommentListResponseDtoList = new ArrayList<>();
        if (commentList == null) {
            throw new CommentNotFoundException("해당 글에는 댓글이 존재하지 않습니다.");
        }
        for (UserComment comment : commentList) {

            if(comment.getParentCommentId() == null) {
                UserCommentListResponseDto commentListResponseDto = new UserCommentListResponseDto(comment,comment.getWriter());
                parentCommentListResponseDtoList.add(commentListResponseDto);
            }
        }
        return ResponseEntity.ok().body(parentCommentListResponseDtoList);
    }
}

