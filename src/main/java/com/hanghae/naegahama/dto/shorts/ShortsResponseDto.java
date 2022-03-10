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

    public ShortsResponseDto(String videoUrl, String title, String nickname, String profileUrl, Long answerId) {
        this.videoUrl = videoUrl;
        this.title = title;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.answerId = answerId;
    }
}
