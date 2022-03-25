package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.comment.CommentModifyRequestDto;
import com.hanghae.naegahama.dto.comment.CommentRequestDto;
import com.hanghae.naegahama.dto.comment.CommentUserPageRequestDto;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{answerId}")
    public ResponseEntity<?> writeComment(@PathVariable Long answerId,
                                          @RequestBody @Validated CommentRequestDto commentRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.writeComment(answerId, commentRequestDto,userDetails.getUser());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> modifyComment(@PathVariable Long commentId, @RequestBody @Validated CommentModifyRequestDto commentModifyRequestDto){
        return commentService.modifyComment(commentId,commentModifyRequestDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        return commentService.deleteComment(commentId);
    }

    @GetMapping("/answer/{answerId}")
    public ResponseEntity<?> getCommentList(@PathVariable Long answerId){
        return commentService.getCommentList(answerId);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getKidsCommentList(@PathVariable Long commentId){
        return commentService.getKidsCommentList(commentId);
    }

    @PostMapping("/userpage/{userId}")
    public ResponseEntity<?> writeComment(@PathVariable Long userId,
                                          @RequestBody @Validated CommentUserPageRequestDto commentUserPageRequestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.writeUserPageComment(userId, commentUserPageRequestDto,userDetails.getUser());
    }



}

