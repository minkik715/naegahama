package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.post.PostRequestDto;
import com.hanghae.naegahama.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping("/api/post")
    public ResponseEntity<?> createPost(@RequestPart(name = "post") PostRequestDto postRequestDto,
                                        @RequestPart(name = "file", required = false) List<MultipartFile> multipartFileList,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        return postService.createPost(multipartFileList,postRequestDto,userDetails.getUser());
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


    // 게시글 전체조회
    @GetMapping("/api/post")
    public ResponseEntity<?> getPost() {
        return ResponseEntity.ok().body(postService.getPost());
    }

    // 게시글 상세조회
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<?> getPost1(@PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.getPost1(postId));
    }


//    //카테고리
//    @GetMapping("/api/post/{category}")
//    public ResponseEntity<?> getCategory(@PathVariable String category) {
//
//        postService.getCategory(category);
//        return ResponseEntity.ok().body(new BasicResponseDto("true"));
//    }
    //카테고리
    @GetMapping("/api/post/category/{category}")
    public ResponseEntity<?> getCategory(@PathVariable String category) {
        return ResponseEntity.ok().body(postService.getCategory(category));
    }
}