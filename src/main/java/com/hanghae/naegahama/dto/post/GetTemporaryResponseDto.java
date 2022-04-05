package com.hanghae.naegahama.dto.post;

import com.hanghae.naegahama.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTemporaryResponseDto
{
    private Long requestId;
    private String title;

    public GetTemporaryResponseDto(Post post )
    {
        this.requestId = post.getId();
        this.title = post.getTitle();

    }
}
