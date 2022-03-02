package com.hanghae.naegahama.dto.comment;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String comment;
    private Long parentCommentId = null;
}
