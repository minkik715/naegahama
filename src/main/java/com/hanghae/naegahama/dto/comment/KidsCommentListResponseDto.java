package com.hanghae.naegahama.dto.comment;

import com.hanghae.naegahama.domain.Comment;
<<<<<<< HEAD
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.initial.HippoURL;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
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

    private String imgUrl;

    private Long userId;
    public KidsCommentListResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.commentWriterId = comment.getUser().getId();
        this.commentWriter = comment.getUser().getNickName();
        this.content = comment.getContent();
        this.modifiedAt = TimeHandler.setModifiedAtComment(comment.getModifiedAt());
        this.imgUrl = comment.getUser().getHippoImage();
        this.userId = comment.getUser().getId();
    }
}
