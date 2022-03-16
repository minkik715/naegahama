package com.hanghae.naegahama.dto.post;

import com.hanghae.naegahama.domain.Post;
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

    public PostResponseDto(Post post,
                           Integer answerCount, Long postLikeCount, String timeSet) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.answerCount = answerCount;
        this.postLikeCount = postLikeCount;
        this.modifiedAt = TimeHandler.setModifiedAtLIst(post.getModifiedAt());
        this.timeSet = timeSet;
        this.status = post.getStatus();
        this.writer = post.getUser().getNickName();

    }
}