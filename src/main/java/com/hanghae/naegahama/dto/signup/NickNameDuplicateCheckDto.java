package com.hanghae.naegahama.dto.signup;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class NickNameDuplicateCheckDto {
    @NotBlank(message = "닉네임 값은 필수입니다.")
    private String nickname;
}
