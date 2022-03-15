package com.hanghae.naegahama.dto.signup;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class NickNameDuplicateCheckDto {
    @NotBlank(message = "닉네임 값은 필수입니다.")
    @Pattern(regexp = "^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{2,8}\\$", message = "{6,12}")
    private String nickname;
}
