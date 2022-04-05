<<<<<<< HEAD
/*package com.hanghae.naegahama.dto.post;
=======
package com.hanghae.naegahama.dto.post;/*package com.hanghae.naegahama.dto.post;
>>>>>>> 8b60231f14d958f54f51d5a9cdfd4c2ff9843004

import com.hanghae.naegahama.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class GetTimeSetDto
{
    private String timeSetStart;
    private String timeSetEnd;

    public GetTimeSetDto(Post post )
    {
        this.timeSetStart = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm"));
        this.timeSetEnd = post.getTimeSet();
    }
}*/
