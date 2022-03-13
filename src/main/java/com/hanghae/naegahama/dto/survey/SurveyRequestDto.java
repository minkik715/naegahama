package com.hanghae.naegahama.dto.survey;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SurveyRequestDto {
    @NotNull(message = "테스트 값은 필수입니다.")
    private Long[] result;
}
