package com.hanghae.naegahama.dto.answer;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter

public class AnswerEncodingRequestDto
{
    @NotNull(message = "url이 비어있습니다.")
    private String videoUrl;

}

