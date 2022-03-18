package com.hanghae.naegahama.dto.post;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.initial.HippoURL;
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String modifiedAt;
    private Integer answerCount;
    private Long postLikeCount;
    private String timeSet;
    private String status;

    private String writer;

    private String imgUrl;

    public PostResponseDto(Post post,
                           Integer answerCount, Long postLikeCount, String timeSet, User user) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.answerCount = answerCount;
        this.postLikeCount = postLikeCount;
        this.modifiedAt = TimeHandler.setModifiedAtLIst(post.getModifiedAt());
        this.timeSet = timeSet;
        this.status = post.getStatus();
        this.writer = user.getNickName();
        this.imgUrl = user.getHippoImage();

    }
}