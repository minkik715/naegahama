package com.hanghae.naegahama.dto.post;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String modifiedAt;
    private Integer answerCount;
    private Integer postLikeCount;
    private String timeSet;
    private String status;

    private String writer;

    private String imgUrl;
    private Long userId;

    public PostResponseDto(Post post,
                           Integer answerCount, Integer postLikeCount, String timeSet, User user) {
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
        this.userId = user.getId();

    }
    public PostResponseDto(Post post, String timeSet) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.answerCount = post.getAnswerList().size();
        this.postLikeCount = post.getPostLikes().size();
        this.modifiedAt = TimeHandler.setModifiedAtLIst(post.getModifiedAt());
        this.timeSet = timeSet;
        this.status = post.getStatus();
        this.writer = post.getUser().getNickName();
        this.imgUrl = post.getUser().getHippoImage();
        this.userId = post.getUser().getId();

    }
}