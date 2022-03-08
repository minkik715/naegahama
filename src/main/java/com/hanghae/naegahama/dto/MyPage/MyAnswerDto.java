package com.hanghae.naegahama.dto.MyPage;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.User;
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
    private String nickname;
    private String imgUrl;
    private String category;
//    private Integer answerCount ;

    public MyAnswerDto(Answer answer, User user)
    {
        this.answerId = answer.getId();
        this.title = answer.getTitle();
        this.modifiedAt = answer.getModifiedAt();
        this.nickname = user.getNickName();
        this.imgUrl = user.getHippoName();
        this.category = answer.getPost().getCategory();

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