
package com.hanghae.naegahama.service;

import com.hanghae.naegahama.repository.commentrepository.CommentQuerydslRepository;
import com.hanghae.naegahama.util.ComfortMethods;
import com.hanghae.naegahama.domain.*;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.comment.*;
import com.hanghae.naegahama.event.CommentWriteEvent;
import com.hanghae.naegahama.ex.CommentNotFoundException;
import com.hanghae.naegahama.repository.commentrepository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentQuerydslRepository commentQuerydslRepository;
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

            applicationEventPublisher.publishEvent(new CommentWriteEvent(findAnswer.getUser(), user,findAnswer, AlarmType.comment));

        } else {
            Comment parentComment = ComfortMethods.getComment(parentCommentId);
            comment = new Comment(
                    commentRequestDto.getComment(),
                    parentComment,
                    findAnswer,
                    user);
            applicationEventPublisher.publishEvent(new CommentWriteEvent(parentComment.getUser(), user, comment, AlarmType.child));

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
        commentRepository.deleteAll(commentQuerydslRepository.findCommentsByParentId(commentId));
        commentRepository.deleteById(commentId);
        return new BasicResponseDto("success");
    }


    @Transactional(readOnly = true)
    public List<CommentListResponseDto> getCommentList(Long answerId) {
        return commentQuerydslRepository.findCommentByAnswer(ComfortMethods.getAnswer(answerId).getId()).stream()
                .map(c -> new CommentListResponseDto(c, c.getUser(), (long) c.getChildCommentList().size()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AllCommentResponseDto getKidsCommentList(Long commentId) {
        Comment parentComment = ComfortMethods.getComment(commentId);
        List<KidsCommentListResponseDto> kidsCommentListResponseDtoList = commentQuerydslRepository.findCommentsByParentId(commentId).stream()
                .map(KidsCommentListResponseDto::new)
                .collect(Collectors.toList());
        return new AllCommentResponseDto(new CommentResponseDto(parentComment, parentComment.getAnswer().getId()), kidsCommentListResponseDtoList);
    }

    
}

