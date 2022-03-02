package com.hanghae.naegahama.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class SignUpRequestDto {
    private String email;
    private String nickname;
    private String password;
    private String passwordCheck;
}
