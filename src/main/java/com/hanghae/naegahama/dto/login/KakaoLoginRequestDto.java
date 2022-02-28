package com.hanghae.naegahama.dto.login;

import lombok.Getter;

@Getter
public class KakaoLoginRequestDto {
    private String kakaoToken;
    public KakaoLoginRequestDto(String kakaoToken) {
        this.kakaoToken = kakaoToken;
    }
}
