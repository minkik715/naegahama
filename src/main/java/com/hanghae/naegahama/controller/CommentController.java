package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.comment.*;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comment/answer/{answerId}")
    public List<CommentListResponseDto> getCommentList(@PathVariable Long answerId){
        return commentService.getCommentList(answerId);
    }

    @GetMapping("/comment/{commentId}")
    public AllCommentResponseDto getKidsCommentList(@PathVariable Long commentId){
        return commentService.getKidsCommentList(commentId);
    }


    @PostMapping("/comment/{answerId}")
    public CommentResponseDto writeComment(@PathVariable Long answerId,
                                                           @RequestBody @Validated CommentRequestDto commentRequestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.writeComment(answerId, commentRequestDto, userDetails.getUser());
    }

    @PutMapping("/comment/{commentId}")
    public BasicResponseDto modifyComment(@PathVariable Long commentId, @RequestBody @Validated CommentModifyRequestDto commentModifyRequestDto){
        return commentService.modifyComment(commentId, commentModifyRequestDto);
    }

    @DeleteMapping("/comment/{commentId}")
    public BasicResponseDto deleteComment(@PathVariable Long commentId){
        return commentService.deleteComment(commentId);
    }



}

