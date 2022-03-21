package com.hanghae.naegahama.dto.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class UserInfoRequestDto {

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;
    @NotBlank(message = "성별은 필수입니다.")
    private String gender;
    @NotBlank(message = "나이는 필수입니다.")
    private String age;
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    private String phoneNumber =null;
}
