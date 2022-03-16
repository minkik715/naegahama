package com.hanghae.naegahama.dto.login;

import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String nickname;

    public UserResponseDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickName();
    }
}
