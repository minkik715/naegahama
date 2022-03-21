package com.hanghae.naegahama.dto.survey;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.PostLike;
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommendResponseDto {

    private Long postid;
    private String title;
    private String content;
    private String modifiedAt;
    private List<PostLike> postLikeCount;
    private String category;

    private Long postid1;
    private String title1;
    private String content1;
    private String modifiedAt1;
    private List<PostLike> postLikeCount1;
    private String category1;

    public CommendResponseDto(Post post1,Post post2) {
        this.postid = post1.getId();
        this.title = post1.getTitle();
        this.content = post1.getContent();
        this.modifiedAt = TimeHandler.setModifiedAtLIst(post1.getModifiedAt());
        this.postLikeCount = post1.getPostLikes();
        this.category = post1.getCategory();

        this.postid1 = post2.getId();
        this.title1 = post2.getTitle();
        this.content1 = post2.getContent();
        this.modifiedAt1 = TimeHandler.setModifiedAtLIst(post2.getModifiedAt());
        this.postLikeCount1 = post2.getPostLikes();
        this.category1 = post2.getCategory();
    }
}
