package com.hanghae.naegahama.dto.survey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyresponseDto {
    private String hippoName;

    public SurveyresponseDto(String hippoName) {
        this.hippoName = hippoName;
    }
}
