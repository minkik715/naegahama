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

    public ShortsResponseDto(String videoUrl, String title, String nickname, String profileUrl, Long answerId, Long postId,String imgUrl) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.answerId = answerId;
        this.postId = postId;
        this.imgUrl = imgUrl;
    }
}
