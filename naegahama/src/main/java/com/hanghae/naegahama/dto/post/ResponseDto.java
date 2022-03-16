package com.hanghae.naegahama.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResponseDto {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime modifiedAt;
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
    public ResponseDto(Long id, String title, String content, LocalDateTime modifiedAt, Integer answerCount,
                       Long user_id,String nickname, Long postLikeCount, List<Long> likeUserId,List <String> fileList,
                       String level, String category,String timeSet,String status)
    {
        this.postId = id;
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
        this.answerCount = answerCount;
        this.user_id = user_id;
        this.nickname = nickname;
        this.postLikeCount = postLikeCount;
        this.likeUserIdList  = likeUserId;
        this.level = level;
        this.fileList = fileList;
        this.category = category;
        this.timeSet = timeSet;
        this.status = status;
    }
}