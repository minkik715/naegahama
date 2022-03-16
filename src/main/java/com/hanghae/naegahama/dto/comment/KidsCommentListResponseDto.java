package com.hanghae.naegahama.dto.comment;

import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class KidsCommentListResponseDto {

    private Long commentId;
    private Long commentWriterId;
    private String commentWriter;
    private String content;
    private String modifiedAt;

    public KidsCommentListResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.commentWriterId = comment.getUser().getId();
        this.commentWriter = comment.getUser().getNickName();
        this.content = comment.getContent();
        this.modifiedAt = TimeHandler.setModifiedAtComment(comment.getModifiedAt());
    }
}
