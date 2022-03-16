package com.hanghae.naegahama.dto.comment;

import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.util.TimeHandler;
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
    private String timestamp;


    public CommentResponseDto(Comment save,Long answerId) {
        this.commentWriter= save.getUser().getNickName();
        this.content = save.getContent();
        this.modifiedAt= TimeHandler.setModifiedAtComment(save.getModifiedAt());
        this.answerId = answerId;
        this.commentId = save.getId();
        this.commentWriterId = save.getUser().getId();
        this.timestamp = save.getTimestamp();
    }
}
