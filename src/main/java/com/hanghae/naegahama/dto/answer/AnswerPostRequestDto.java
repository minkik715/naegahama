package com.hanghae.naegahama.dto.answer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AnswerPostRequestDto
{

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message="내용은 필수입니다.")
    private String content;

}

