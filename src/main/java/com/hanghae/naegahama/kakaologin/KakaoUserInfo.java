package com.hanghae.naegahama.kakaologin;

import lombok.Getter;

@Getter
public class KakaoUserInfo {
    private Long id;
    private String nickname;
    private String email;

    public KakaoUserInfo(Long id,String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }


}
