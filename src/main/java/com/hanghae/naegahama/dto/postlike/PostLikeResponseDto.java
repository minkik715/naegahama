package com.hanghae.naegahama.dto.postlike;

import lombok.Getter;

@Getter
public class PostLikeResponseDto {
    private Long postId;
    private Long likeCount;

    public PostLikeResponseDto(Long postId, Long likeCount){
        this.postId = postId;
        this.likeCount = likeCount;
    }
}
