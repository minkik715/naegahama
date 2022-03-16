package com.hanghae.naegahama.dto.post;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String modifiedAt;
    private Integer answerCount;
    private Long postLikeCount;
    private String timeSet;
    private String status;

    private String writer;



    public PostResponseDto(Long id, String title, String content,
                           Integer answerCount, Long postLikeCount, String timeSet, String status, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.answerCount = answerCount;
        this.postLikeCount = postLikeCount;
        this.timeSet = timeSet;
        this.status = status;
        this.writer = writer;

    }
}