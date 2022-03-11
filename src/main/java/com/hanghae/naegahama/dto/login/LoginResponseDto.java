package com.hanghae.naegahama.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String token;
    private String UserStatus;
}
