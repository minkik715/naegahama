package com.hanghae.naegahama.dto.post;

import com.hanghae.naegahama.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyPostDto
{
    private Long requestId;
    private String title;
//    private String content;
    private LocalDateTime modifiedAt;
    private Long likeCount;
//    private Integer answerCount ;

    public MyPostDto(Post post, Long likeCount)
    {
        this.requestId = post.getId();
        this.title = post.getTitle();
        this.modifiedAt = post.getModifiedAt();
        this.likeCount = likeCount;
    }


////
//    @Override
//    public int compareTo(PostMyPageDto postMyPageDto)
//    {
//
//        if (this.createdAt.isAfter(postMyPageDto.getCreatedAt() ))
//        {
//            return -1;
//        }
//        else if (this.createdAt.isBefore(postMyPageDto.getCreatedAt() ))
//        {
//            return 1;
//        }
//        return 0;
//
//    }

}