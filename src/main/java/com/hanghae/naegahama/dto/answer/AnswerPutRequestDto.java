package com.hanghae.naegahama.dto.answer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class AnswerPutRequestDto
{
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message="내용은 필수입니다.")
    private String content;
    private List<String> file;
    private String video;



}

