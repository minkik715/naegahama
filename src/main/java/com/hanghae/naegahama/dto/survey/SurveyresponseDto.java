package com.hanghae.naegahama.dto.survey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyresponseDto {
    private String hippoName;
    private String imgUrl;
    private String surveyResult;

    public SurveyresponseDto(String hippoName, String imgUrl, String surveyResul) {
        this.hippoName = hippoName;
        this.imgUrl = imgUrl;
        this.surveyResult = surveyResult;


    }
}
