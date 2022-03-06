package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.post.PostRequestDto;
import com.hanghae.naegahama.dto.post.PutRequestDto;
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

    // 요청글 작성
    @PostMapping("/api/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto postRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException
    {
        return postService.createPost(postRequestDto,userDetails.getUser());
    }

    // 요청글 임시 저장
    @PostMapping("/api/post/temporary")
    public ResponseEntity<?> temporaryPost(@RequestBody PostRequestDto postRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        return postService.temporaryPost(postRequestDto,userDetails.getUser());
    }

    // 임시 저장 글 리스트 불러오기
    @GetMapping("/api/post/temporary")
    public ResponseEntity<?> temporaryLoad(@AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return postService.temporaryLoad(userDetails);
    }

    // 요청글 전체조회
    @GetMapping("/api/post")
    public ResponseEntity<?> getPost() {
        return ResponseEntity.ok().body(postService.getPost());
    }

    //카테고리
    @GetMapping("/api/post/category/{category}")
    public ResponseEntity<?> getCategory(@PathVariable String category) {
        return ResponseEntity.ok().body(postService.getCategory(category));
    }

    // 요청글 수정
    @PutMapping("/api/post/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PutRequestDto putRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return postService.updatePost(postId, putRequestDto, userDetails);
    }




//    //요청글 삭제
//    @DeleteMapping("/api/post/{postId}")
//    public ResponseEntity<?> deletePost(@PathVariable Long postId,
//                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        postService.deletePost(postId, userDetails);
//
//        return ResponseEntity.ok().body(new BasicResponseDto("true"));
//    }




    // 요청글 상세조회
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<?> getPost1(@PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.getPost1(postId));
    }


}