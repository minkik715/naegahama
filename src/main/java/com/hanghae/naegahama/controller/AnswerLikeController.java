package com.hanghae.naegahama.controller;
import com.hanghae.naegahama.dto.answerlike.AnswerLikeResponseDto;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.AnswerLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AnswerLikeController {

    private final AnswerLikeService answerLikeService;

    @PostMapping("api/answer/like/{answerId}")
    public AnswerLikeResponseDto AnswerLike(@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return answerLikeService.AnswerLike(answerId, userDetails);
    }
}

