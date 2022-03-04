
package com.hanghae.naegahama.service;

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

    @Transactional
    public ResponseEntity<?> writeComment(Long answerId, CommentRequestDto commentRequestDto, User user) {
        Answer findAnswer = answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerNotFoundException("해당 답글이 존재하지 않습니다.")
        );
        String commentContent = commentRequestDto.getComment();
        Long parentCommentId = commentRequestDto.getParentCommentId();
        Comment comment = null;
        if(parentCommentId == null) {
            comment = new Comment(commentContent, findAnswer, user);
            user.getCommentList().add(comment);
        }else{
            comment = new Comment(commentContent,parentCommentId, findAnswer, user);
            user.getCommentList().add(comment);
        }
        Comment save = commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(save,answerId);

        return ResponseEntity.ok().body(commentResponseDto);
        // 최초 요청글 작성시 업적 4 획득
/*        User achievementUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("업적 달성 유저가 존재하지 않습니다."));
        achievementUser.getAchievement().setAchievement4(1);*/


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
                //보낼 대댓글 리스트
                List<CommentListResponseDto> childCommentListResponseDto = new ArrayList<>();
                List<Comment> kidsCommentList = commentRepository.findAllByParentCommentId(comment.getId());
                log.info("id={}",comment.getId());

                for (Comment comment1 : kidsCommentList) {
                    log.info("id={}",comment1.getId());
                    CommentListResponseDto commentListResponseDto = new CommentListResponseDto(comment1);
                    childCommentListResponseDto.add(commentListResponseDto);
                }
                CommentListResponseDto commentListResponseDto = new CommentListResponseDto(comment, childCommentListResponseDto);


                parentCommentListResponseDtoList.add(commentListResponseDto);
            }
        }
        return ResponseEntity.ok().body(parentCommentListResponseDtoList);

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

