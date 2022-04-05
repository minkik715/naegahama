package com.hanghae.naegahama.dto.survey;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

@Getter
@Setter
public class SurveyRequestDto {
    @NotNull(message = "테스트 값은 필수입니다.")
    private Long[] result;
}
