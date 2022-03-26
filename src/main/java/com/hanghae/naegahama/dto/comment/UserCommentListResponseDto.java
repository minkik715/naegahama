package com.hanghae.naegahama.dto.comment;

import com.hanghae.naegahama.domain.Comment;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.domain.UserComment;
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter @NoArgsConstructor
public class UserCommentListResponseDto {
    private Long answerId;
    private Long commentId;
    private Long commentWriterId;
    private String commentWriter;
    private String content;
    private String modifiedAt;

    private String imgUrl;


    public UserCommentListResponseDto(UserComment comment, User user) {
        this.answerId = comment.getPageUser().getId();
        this.commentId = comment.getId();
        this.commentWriterId = user.getId();
        this.commentWriter = user.getNickName();
        this.content = comment.getContent();
        this.modifiedAt = TimeHandler.setModifiedAtComment(comment.getModifiedAt());
        this.imgUrl = user.getHippoImage();
    }
}
