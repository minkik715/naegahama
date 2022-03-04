package com.hanghae.naegahama.dto.comment;

import com.hanghae.naegahama.domain.Comment;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentListResponseDto {
    private Long answerId;
    private Long commentId;
    private Long commentWriterId;
    private String commentWriter;
    private String content;
    private String modifiedAt;

    List<CommentListResponseDto> childCommentListResponseDto;

    public CommentListResponseDto(Comment comment, List<CommentListResponseDto> childCommentListResponseDto) {
        this.answerId = comment.getAnswer().getId();
        this.commentId = comment.getId();
        this.commentWriterId = comment.getUser().getId();
        this.commentWriter = comment.getUser().getNickName();
        this.content = comment.getContent();
        this.modifiedAt = comment.getModifiedAt().toString();
        this.childCommentListResponseDto = childCommentListResponseDto;
    }
    public CommentListResponseDto(Comment comment ) {
        this.answerId = comment.getAnswer().getId();
        this.commentId = comment.getId();
        this.commentWriterId = comment.getUser().getId();
        this.commentWriter = comment.getUser().getNickName();
        this.content = comment.getContent();
        this.modifiedAt = comment.getModifiedAt().toString();
    }
}
