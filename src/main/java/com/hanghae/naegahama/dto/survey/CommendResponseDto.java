package com.hanghae.naegahama.dto.survey;

import com.hanghae.naegahama.domain.Post;
<<<<<<< HEAD
import com.hanghae.naegahama.domain.PostLike;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.Setter;

<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
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
