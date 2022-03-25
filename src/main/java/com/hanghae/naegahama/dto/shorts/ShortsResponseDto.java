package com.hanghae.naegahama.dto.shorts;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class ShortsResponseDto {

    private String videoUrl;
    private String title;
    private String nickname;
    private String profileUrl;
    private Long answerId;
    private Long postId;
    private String imgUrl;
    private Long commentCnt;

    public ShortsResponseDto(String videoUrl, String title, String nickname, String profileUrl, Long answerId, Long postId,String imgUrl, Long commentCnt) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.answerId = answerId;
        this.postId = postId;
        this.imgUrl = imgUrl;
        this.commentCnt = commentCnt;
    }
}
