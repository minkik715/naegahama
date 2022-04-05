package com.hanghae.naegahama.dto.post;

import com.hanghae.naegahama.domain.Post;
import lombok.Getter;
import lombok.Setter;

<<<<<<< HEAD
import java.time.LocalDateTime;

=======
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004
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
