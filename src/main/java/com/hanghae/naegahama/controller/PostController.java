package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.post.PostRequestDto;
import com.hanghae.naegahama.dto.post.PostResponseDto;
import com.hanghae.naegahama.dto.post.PutRequestDto;
import com.hanghae.naegahama.dto.post.ResponseDto;
import com.hanghae.naegahama.security.UserDetailsImpl;
import com.hanghae.naegahama.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    // 요청글 작성
    @PostMapping("/api/post")
    public Long createPost(@RequestBody @Validated PostRequestDto postRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return postService.createPost(postRequestDto, userDetails);
    }

    // 요청글 전체조회
    @GetMapping("/api/post")
    public List<PostResponseDto> getPost() {
        return postService.getPost();
    }

    //카테고리
    @GetMapping("/api/post/category/{category}")
    public List<PostResponseDto> getCategory(@PathVariable String category) {
        return postService.getCategory(category);
    }

    // 요청글 수정
    @PutMapping("/api/post/{postId}")
    public BasicResponseDto updatePost(@PathVariable Long postId, @RequestBody @Validated PutRequestDto putRequestDto,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return postService.updatePost(postId, putRequestDto, userDetails);
    }


    // 요청글 상세조회
    @GetMapping("/api/post/{postId}")
    public ResponseDto getPost1(@PathVariable Long postId) {
        return postService.getPost1(postId);
    }

    @GetMapping("/api/post/category/{category}/{sort}")
    public List<PostResponseDto>  getPostByCategoryAndSort(@PathVariable String category, @PathVariable String sort){
        return postService.getPostByCategoryAndSort(category, sort);
    }
    //요청글 마감
    @PostMapping("/api/post/{postId}")
    public BasicResponseDto finishPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.finishPost(postId, userDetails.getUser());
    }


    @GetMapping("/api/post/admin")
    public List<PostResponseDto> getAdminPost(){
        return postService.getAdminPost();
    }



}