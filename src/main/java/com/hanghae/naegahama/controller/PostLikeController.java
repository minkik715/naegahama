package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.postlike.PostLikeResponseDto;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/api/post/like/{postId}")
    public PostLikeResponseDto postLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postLikeService.PostLike(postId, userDetails);
    }

}
