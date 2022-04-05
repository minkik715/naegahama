package com.hanghae.naegahama.dto.comment;

<<<<<<< HEAD
import com.hanghae.naegahama.domain.Comment;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.hanghae.naegahama.domain.UserComment;
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCommentResponseDto {
    private Long pageUserId;
    private Long commentId;
    private Long commentWriterId;
    private String commentWriter;
    private String content;
    private String modifiedAt;
    private String timestamp;
    private String imgUrl;


    public UserCommentResponseDto(UserComment save, Long pageUserId) {
        this.commentWriter= save.getWriter().getNickName();
        this.content = save.getContent();
        this.modifiedAt= TimeHandler.setModifiedAtComment(save.getModifiedAt());
        this.pageUserId = pageUserId;
        this.commentId = save.getId();
        this.commentWriterId = save.getWriter().getId();
        this.imgUrl = save.getWriter().getHippoImage();

    }
}
