package com.hanghae.naegahama.dto.post;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.Setter;

<<<<<<< HEAD
import java.time.LocalDateTime;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import java.util.List;

@Getter
@Setter
public class ResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String modifiedAt;
    private Integer answerCount;
    private Long user_id;
    private String nickname;
    private Long postLikeCount;
    private String level;
    private List<Long> likeUserIdList;
    private List <String> fileList;

    private String category;

    private String timeSet;
    private String status;
    
    private Long userId;
    public ResponseDto(Post post,  Integer answerCount, Long postLikeCount, List<Long> likeUserId, List <String> fileList,
                        String timeSet)
    {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.modifiedAt = TimeHandler.setModifiedAtLIst(post.getModifiedAt());
        this.answerCount = answerCount;
        this.user_id = post.getUser().getId();
        this.nickname = post.getUser().getNickName();
        this.postLikeCount = postLikeCount;
        this.likeUserIdList  = likeUserId;
        this.level = post.getLevel();
        this.fileList = fileList;
        this.category = post.getCategory();
        this.timeSet = timeSet;
        this.status = post.getStatus();
        this.userId = post.getUser().getId();
    }
}