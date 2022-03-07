package com.hanghae.naegahama.dto.post;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private String category;
    private String level;
    private List<String> file;
    private Long timeSet;
}
