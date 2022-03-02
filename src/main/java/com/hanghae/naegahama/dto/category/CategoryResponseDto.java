package com.hanghae.naegahama.dto.category;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime modifiedAt;
    private Integer answerCount;


    public CategoryResponseDto(Long id, String title, String content, LocalDateTime modifiedAt, Integer answerCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
        this.answerCount = answerCount;
    }
}
