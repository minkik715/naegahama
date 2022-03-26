package com.hanghae.naegahama.dto.userpagecommentdto;


import com.hanghae.naegahama.domain.UserComment;
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor @Getter @Setter
public class UserCommentResponseDto {

    private Long userId;
    private Long commentId;
    private Long commentWriterId;
    private String commentWriter;
    private String content;
    private String modifiedAt;
    private String imgUrl;
    private ArrayList<UserCommentResponseDto> childComments = new ArrayList<>();

    public UserCommentResponseDto(UserComment userComment) {
        this.userId = userComment.getPageUser().getId();
        this.commentId = userComment.getId();
        this.commentWriterId = userComment.getWriter().getId();
        this.commentWriter = userComment.getWriter().getNickName();
        this.content = userComment.getContent();
        this.modifiedAt = TimeHandler.setModifiedAtComment(userComment.getModifiedAt());
        this.imgUrl = userComment.getWriter().getHippoImage();
    }
}
