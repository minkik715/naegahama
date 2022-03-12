package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/api/post/like/{postId}")
    public ResponseEntity<?> postLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        postLikeService.PostLike(postId, userDetails.getUser().getId());
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

}
