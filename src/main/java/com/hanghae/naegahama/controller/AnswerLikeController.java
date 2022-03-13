package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.AnswerLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AnswerLikeController {

    private final AnswerLikeService answerLikeService;

    @PostMapping("api/answer/like/{answerId}")
    public ResponseEntity<?> AnswerLike(@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        answerLikeService.AnswerLike(answerId, userDetails.getUser().getId());
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }
}

