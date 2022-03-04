package com.hanghae.naegahama.dto.comment;

import com.hanghae.naegahama.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private String content;
    private String createdAt;
    private String commentWriter;

    public CommentResponseDto(Comment save) {
        this.commentWriter= save.getUser().getNickName();
        this.content = save.getContent();
        this.createdAt= save.getCreatedAt().toString();
    }
}
