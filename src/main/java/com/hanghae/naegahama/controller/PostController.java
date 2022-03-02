package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.post.PostRequestDto;
import com.hanghae.naegahama.dto.post.PostResponseDto;
import com.hanghae.naegahama.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;


    // 게시글 작성
    @PostMapping("/api/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto postRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.createPost(postRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    // 게시글 전체조회
    @GetMapping("/api/post")
    public ResponseEntity<?> getPost() {
        postService.getPost();
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    // 게시글 수정
    @PutMapping("/api/post/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long postId,
            PostRequestDto postRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.updatePost(postId, postRequestDto, userDetails);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    //게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    // 게시글 상세조회
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<?> getPost1(@PathVariable Long postId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.getPost1(postId, userDetails);

        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    //카테고리
    @GetMapping("/api/post/{category}")
    public ResponseEntity<?> getCategory(@PathVariable String category) {

        postService.getCategory(category);
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }
}