package com.hanghae.naegahama.dto.signup;

<<<<<<< HEAD
import com.sun.istack.NotNull;
=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class NickNameDuplicateCheckDto {
    @NotBlank(message = "닉네임 값은 필수입니다.")
    @Pattern(regexp = "[가-힣ㄱ-ㅎa-zA-Z0-9._-]{2,8}")
    private String nickname;
}
