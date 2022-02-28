package com.hanghae.naegahama.dto.signup;

import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String email;
    private String nickname;
    private String password;
    private String passwordCheck;
}
