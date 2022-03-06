package com.hanghae.naegahama.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime modifiedAt;
    private Integer answerCount;
    private Long postLikeCount;



    public CategoryResponseDto(Long id, String title, String content,
                               LocalDateTime modifiedAt, Integer answerCount, Long postLikeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
        this.answerCount = answerCount;
        this.postLikeCount = postLikeCount;

    }
}
