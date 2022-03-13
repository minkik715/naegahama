package com.hanghae.naegahama.dto.comment;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentRequestDto {

    @NotBlank(message = "댓글내용은 필수입니다.")
    private String comment;
    private String timestamp = null;
    private Long parentCommentId = null;
}
