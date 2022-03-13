package com.hanghae.naegahama.dto.comment;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentModifyRequestDto {
    @NotBlank(message = "댓글내용은 필수입니다.")
    private String content;
    private String timestamp;
}
