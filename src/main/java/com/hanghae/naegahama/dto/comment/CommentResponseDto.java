package com.hanghae.naegahama.dto.comment;

import com.hanghae.naegahama.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long answerId;
    private Long commentId;
    private Long commentWriterId;
    private String commentWriter;
    private String content;
    private String modifiedAt;


    public CommentResponseDto(Comment save,Long answerId) {
        this.commentWriter= save.getUser().getNickName();
        this.content = save.getContent();
        this.modifiedAt= save.getCreatedAt().toString();
        this.answerId = answerId;
        this.commentId = save.getId();
        this.commentWriterId = save.getUser().getId();
    }
}
