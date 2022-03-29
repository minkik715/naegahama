
package com.hanghae.naegahama.service;

import com.hanghae.naegahama.comfortmethod.ComfortMethods;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.comment.*;
import com.hanghae.naegahama.handler.event.CommentWriteEvent;
import com.hanghae.naegahama.ex.AnswerNotFoundException;
import com.hanghae.naegahama.ex.CommentNotFoundException;
import com.hanghae.naegahama.repository.AnswerRepository;
import com.hanghae.naegahama.repository.CommentRepository;
import com.hanghae.naegahama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    public CommentResponseDto writeComment(Long answerId, CommentRequestDto commentRequestDto, User user) {
        Answer findAnswer = ComfortMethods.getAnswer(answerId);
        Long parentCommentId = commentRequestDto.getParentCommentId();
        Comment comment = null;
        if (parentCommentId == null) {
            String timestamp = commentRequestDto.getTimestamp();
            comment = new Comment(
                    commentRequestDto.getComment(),
                    findAnswer,
                    user,
                    timestamp
            );
            //답변글에 댓글을 단 사람에게 주는 알람.(대댓글 미포함 하고싶음.)
            applicationEventPublisher.publishEvent(new CommentWriteEvent(findAnswer.getUser(), user,findAnswer, AlarmType.comment));

        } else {
            comment = new Comment(commentRequestDto.getComment(),
                    parentCommentId,
                    findAnswer,
                    user);
                Comment findcomment = commentRepository.findById(parentCommentId).orElseThrow(
                        () -> new CommentNotFoundException("댓글 없습니다.")
                );
            applicationEventPublisher.publishEvent(new CommentWriteEvent(findcomment.getUser(), user, comment, AlarmType.child));

        }
        Comment save = commentRepository.save(comment);
        return new CommentResponseDto(save, answerId);
    }


    public BasicResponseDto modifyComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다.")
        );
        findComment.setComment(commentModifyRequestDto.getContent(), commentModifyRequestDto.getTimestamp());
        return new BasicResponseDto("success");
    }


    public BasicResponseDto deleteComment(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다.")
        );
        commentRepository.deleteAll(commentRepository.findAllByParentCommentId(commentId));
        commentRepository.deleteById(commentId);
        return new BasicResponseDto("success");
    }


    @Transactional(readOnly = true)
    public List<CommentListResponseDto> getCommentList(Long answerId) {
        Answer findAnswer = ComfortMethods.getAnswer(answerId);
        List<CommentListResponseDto> parentCommentListResponseDtoList = new ArrayList<>();

        for (Comment comment : findAnswer.getCommentList()) {

            if(comment.getParentCommentId() == null) {

                CommentListResponseDto commentListResponseDto = new CommentListResponseDto(
                        comment,
                        comment.getUser(),
                        commentRepository.countByParentCommentId(comment.getId())
                );
                parentCommentListResponseDtoList.add(commentListResponseDto);
            }
        }
        return parentCommentListResponseDtoList;

    }

    @Transactional(readOnly = true)
    public AllCommentResponseDto getKidsCommentList(Long commentId) {
        Comment parentComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글은 존재하지 않습니다.")
        );
        List<KidsCommentListResponseDto> kidsCommentListResponseDtoList = new ArrayList<>();

        for (Comment comment : commentRepository.findAllByParentCommentId(commentId)) {
            KidsCommentListResponseDto kidsCommentListResponseDto = new KidsCommentListResponseDto(comment, comment.getUser());
            kidsCommentListResponseDtoList.add(kidsCommentListResponseDto);

        }
        return new AllCommentResponseDto(new CommentResponseDto(parentComment, parentComment.getAnswer().getId()), kidsCommentListResponseDtoList);
    }

    
}

