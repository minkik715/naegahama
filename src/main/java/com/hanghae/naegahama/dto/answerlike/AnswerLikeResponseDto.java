package com.hanghae.naegahama.dto.answerlike;

import lombok.Getter;

@Getter
public class AnswerLikeResponseDto {
    private Long postId;
    private Long likeCount;

    public AnswerLikeResponseDto(Long postId, Long likeCount){
        this.postId = postId;
        this.likeCount = likeCount;
    }
}
