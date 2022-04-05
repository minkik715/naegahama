package com.hanghae.naegahama.dto.survey;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommendResponseDto {

    private Long postid;
    private String title;
    private String content;
    private String modifiedAt;
    private Long postLikeCount;
    private String category;


    public CommendResponseDto(Post post, Long count) {
        this.postid = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.modifiedAt = TimeHandler.setModifiedAtLIst(post.getModifiedAt());
        this.postLikeCount = count;
        this.category = post.getCategory();

    }
}
