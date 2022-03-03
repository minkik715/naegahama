package com.hanghae.naegahama.dto.MyPage;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyAnswerDto
{
    private Long answerId;
    private String title;
//    private String content;
    private LocalDateTime modifiedAt;
    private Long likeCount;
//    private Integer answerCount ;

    public MyAnswerDto(Answer answer, Long likeCount)
    {
        this.answerId = answer.getId();
        this.title = answer.getTitle();
        this.modifiedAt = answer.getModifiedAt();
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