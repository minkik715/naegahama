package com.hanghae.naegahama.dto.answer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnswerPostRequestDto
{
    
    private String title;

    private String content;

    private List<String> file;

    private String video;
}

