//package com.hanghae.naegahama.controller;
//
//import com.hanghae.naegahama.config.auth.UserDetailsImpl;
//import com.hanghae.naegahama.dto.BasicResponseDto;
//import com.hanghae.naegahama.dto.post.PostRequestDto;
//import com.hanghae.naegahama.service.SurveyService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//public class SurveyController {
//
//    private final SurveyService surveyService;
//
//    @PostMapping("/api/survey")
//    public ResponseEntity<?> createSurvey(@RequestBody PostRequestDto postRequestDto,
//                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        postService.createPost(postRequestDto, userDetails.getUser());
//        return ResponseEntity.ok().body(new BasicResponseDto("true"));
//    }
//}
