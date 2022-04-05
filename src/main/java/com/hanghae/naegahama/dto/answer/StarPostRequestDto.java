package com.hanghae.naegahama.dto.answer;

import lombok.Getter;

<<<<<<< HEAD
import javax.validation.constraints.NotBlank;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import javax.validation.constraints.NotNull;

@Getter

public class StarPostRequestDto
{
    @NotNull(message = "점수를 빈값일 수 없습니다.")
    private Integer star;

}

