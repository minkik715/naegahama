package com.hanghae.naegahama.dto.answer;

import lombok.Getter;
import javax.validation.constraints.NotNull;

@Getter

public class StarPostRequestDto
{
    @NotNull(message = "점수를 빈값일 수 없습니다.")
    private Integer star;

}

