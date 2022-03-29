package com.hanghae.naegahama.controller;
import com.hanghae.naegahama.dto.survey.CommendResponseDto;
import com.hanghae.naegahama.dto.survey.SurveyresponseDto;
import com.hanghae.naegahama.security.UserDetailsImpl;

import com.hanghae.naegahama.dto.BasicResponseDto;
import com.hanghae.naegahama.dto.survey.SurveyRequestDto;
import com.hanghae.naegahama.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SurveyController {

    private final SurveyService surveyService;

    //설문조사 제출
    @PostMapping("/api/survey")
    public BasicResponseDto createHippo(@RequestBody @Validated SurveyRequestDto surveyRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return  surveyService.createHippo(surveyRequestDto, userDetails.getUser());
    }

    //설문조사 결과
    @GetMapping("/api/survey")
    public SurveyresponseDto getHippo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return surveyService.getHippo(userDetails);
    }

    //같은 하마의 요청글 추천.
    @GetMapping("/api/survey/{hippoName}")
    public List<CommendResponseDto> recommend(@PathVariable String hippoName) {
        return surveyService.recommend(hippoName);
    }

}
