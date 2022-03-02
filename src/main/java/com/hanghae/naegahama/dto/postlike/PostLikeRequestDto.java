package com.hanghae.naegahama.dto.postlike;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PostLikeRequestDto {
    private User user;
    private Post post;
}
