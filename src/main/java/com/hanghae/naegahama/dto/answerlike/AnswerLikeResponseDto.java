package com.hanghae.naegahama.dto.answerlike;

import lombok.Getter;

@Getter
public class AnswerLikeResponseDto {
    private Long postId;
    private int likeCount;

    public AnswerLikeResponseDto(Long postId, int likeCount){
        this.postId = postId;
        this.likeCount = likeCount;
    }
}
