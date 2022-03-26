package com.hanghae.naegahama.dto.userpagecommentdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter
@Setter
public class UserCommentRequestDto {
    private String content;
    private Long parentId =null;
}
