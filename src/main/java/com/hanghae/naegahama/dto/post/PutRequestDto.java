package com.hanghae.naegahama.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PutRequestDto
{
    private String content;
    private List<String> file;
}
