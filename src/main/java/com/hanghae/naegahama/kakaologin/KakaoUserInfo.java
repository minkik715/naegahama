package com.hanghae.naegahama.kakaologin;

import lombok.Getter;

@Getter
public class KakaoUserInfo {
    private String nickname;
    private String email;

    public KakaoUserInfo(String nickname, String email)
    {
        this.nickname = nickname;
        this.email = email;
    }


}
