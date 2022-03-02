package com.hanghae.naegahama.dto.post;

import com.hanghae.naegahama.domain.Answer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime modifiedAt;
    private Integer answerCount;
    private Long postLikeCount;

    public PostResponseDto(Long id, String title, String content, LocalDateTime modifiedAt,
                           Integer answerCount, Long postLikeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
        this.answerCount = answerCount;
        this.postLikeCount = postLikeCount;
    }
}