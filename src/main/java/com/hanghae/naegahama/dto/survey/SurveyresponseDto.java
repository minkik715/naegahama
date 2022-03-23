package com.hanghae.naegahama.dto.survey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SurveyresponseDto {
    private String hippoName;
    private String imgUrl;
    private String nickname;
    private String surveyResult;

    public SurveyresponseDto(String nickname,String hippoName, String imgUrl, String surveyResult) {
        this.hippoName = hippoName;
        this.imgUrl = imgUrl;
        this.surveyResult = surveyResult;
        this.nickname = nickname;


    }
}
