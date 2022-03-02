package com.hanghae.naegahama.dto.post;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostMyPageDto implements Comparable<PostMyPageDto>
{
    private Long requestId;
    private Long answerId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
//    private Integer answerCount ;

    public PostMyPageDto(Post post)
    {
        this.requestId = post.getId();
        this.answerId = null;
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }

    public PostMyPageDto(Answer answer)
    {
        this.requestId = null;
        this.answerId = answer.getId();
        this.title = answer.getTitle();
        this.content = answer.getContent();
        this.createdAt = answer.getCreatedAt();
    }

    @Override
    public int compareTo(PostMyPageDto postMyPageDto)
    {

        if (this.createdAt.isAfter(postMyPageDto.getCreatedAt() ))
        {
            return -1;
        }
        else if (this.createdAt.isBefore(postMyPageDto.getCreatedAt() ))
        {
            return 1;
        }
        return 0;

    }

}