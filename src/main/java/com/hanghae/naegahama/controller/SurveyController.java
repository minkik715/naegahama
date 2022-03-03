package com.hanghae.naegahama.controller;

import com.hanghae.naegahama.config.auth.UserDetailsImpl;
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
}

//    //설문조사 결과
//    @GetMapping("/api/survey")
//    public ResponseEntity<?> getHippo() {
//        surveyService.getHippo();
//        return ResponseEntity.ok().body(new BasicResponseDto("true"));
//    }
//
//    //설문결과  추천 요청글.
//    @GetMapping("/api/survey/{hippoName}")
//    public ResponseEntity<?> recommend(@PathVariable String hippoName,
//                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        surveyService.recommend(hippoName, userDetails);
//        return ResponseEntity.ok().body(new BasicResponseDto("true"));
//    }
//
//
//}
