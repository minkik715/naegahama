package com.hanghae.naegahama.service;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.comment.CommentListResponseDto;
import com.hanghae.naegahama.dto.comment.CommentModifyRequestDto;
import com.hanghae.naegahama.dto.comment.CommentRequestDto;
import com.hanghae.naegahama.dto.comment.KidsCommentListResponseDto;
import com.hanghae.naegahama.handler.ex.AnswerNotFoundException;
import com.hanghae.naegahama.handler.ex.CommentNotFoundException;
import com.hanghae.naegahama.repository.AnswerRepository;
import com.hanghae.naegahama.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;

    public ResponseEntity<?> writeComment(Long answerId, CommentRequestDto commentRequestDto, User user) {
        Answer findAnswer = answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글이 존재하지 않습니다.")
        );
        String commentContent = commentRequestDto.getComment();
        Long parentCommentId = commentRequestDto.getParentCommentId();
        Comment comment = null;
        if(parentCommentId == null) {
            comment = new Comment(commentContent, findAnswer, user);
        }else{
            comment = new Comment(commentContent,parentCommentId, findAnswer, user);
        }
        commentRepository.save(comment);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public ResponseEntity<?> modifyComment(Long commentId, CommentModifyRequestDto commentModifyRequestDto) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다.")
        );
        findComment.setContent(commentModifyRequestDto.getContent());
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public ResponseEntity<?> deleteComment(Long commentId) {
        Comment findComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다.")
        );
        commentRepository.deleteById(commentId);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    public ResponseEntity<?> getParentCommentList(Long answerId) {
        Answer findAnswer = answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글이 존재하지 않습니다.")
        );
        List<Comment> commentList = findAnswer.getCommentList();
        List<CommentListResponseDto> commentListResponseDtoList = new ArrayList<>();
        if(commentList == null){
            throw new CommentNotFoundException("해당 글에는 댓글이 존재하지 않습니다.");
        }
        for (Comment comment : commentList) {
            if(comment.getParentCommentId() == null) {
                CommentListResponseDto commentListResponseDto = new CommentListResponseDto(comment);
                commentListResponseDtoList.add(commentListResponseDto);

            }
        }
        return ResponseEntity.ok().body(commentListResponseDtoList);

    }

    public ResponseEntity<?> getKidsCommentList(Long commentId) {
        List<Comment> kidsCommentList = commentRepository.findAllByParentCommentId(commentId);
        List<KidsCommentListResponseDto> kidsCommentListResponseDtoList = new ArrayList<>();
        if(kidsCommentList == null){
            throw new CommentNotFoundException("해당 댓글에는 대댓글이 존재하지 않습니다.");
        }
        for (Comment comment : kidsCommentList) {
            KidsCommentListResponseDto kidsCommentListResponseDto = new KidsCommentListResponseDto(comment);
            kidsCommentListResponseDtoList.add(kidsCommentListResponseDto);

        }
        return ResponseEntity.ok().body(kidsCommentListResponseDtoList);
    }
}
