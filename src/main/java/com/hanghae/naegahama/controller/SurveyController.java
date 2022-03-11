package com.hanghae.naegahama.controller;
import com.hanghae.naegahama.security.UserDetailsImpl;

import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.survey.SurveyRequestDto;
import com.hanghae.naegahama.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SurveyController {

    private final SurveyService surveyService;

    //설문조사 제출
    @PostMapping("/api/survey")
    public ResponseEntity<?> createHippo(@RequestBody SurveyRequestDto surveyRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        surveyService.createHippo(surveyRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new BasicResponseDto("true"));
    }

    //설문조사 결과
    @GetMapping("/api/survey")
    public ResponseEntity<?> getHippo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(surveyService.getHippo(userDetails));
    }

    //같은 하마의 요청글 추천.
    @GetMapping("/api/survey/{hippoName}")
    public ResponseEntity<?> recommend(@PathVariable String hippoName) {
        return ResponseEntity.ok().body(surveyService.recommend(hippoName));
    }
}
