package com.hanghae.naegahama.dto.comment;

import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.initial.HippoURL;
import com.hanghae.naegahama.util.TimeHandler;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter @NoArgsConstructor @Data
public class CommentListResponseDto {
    private Long answerId;
    private Long commentId;
    private Long commentWriterId;
    private String commentWriter;
    private String content;
    private String modifiedAt;

    private String timestamp;

    private String imgUrl;

    private Long childCnt;
    
    private Long userId;


    @QueryProjection
    public CommentListResponseDto(Comment comment, User user, Long childCnt) {
        this.answerId = comment.getAnswer().getId();
        this.commentId = comment.getId();
        this.commentWriterId = user.getId();
        this.commentWriter = user.getNickName();
        this.content = comment.getContent();
        this.modifiedAt = TimeHandler.setModifiedAtComment(comment.getModifiedAt());
        this.timestamp = comment.getTimestamp();
        this.imgUrl = user.getHippoImage();
        this.childCnt = childCnt;
        this.userId = user.getId();
    }
}
