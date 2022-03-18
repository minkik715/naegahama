package com.hanghae.naegahama.dto.MyPage;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.initial.HippoURL;
import com.hanghae.naegahama.util.TimeHandler;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyAnswerDto
{
    private Long answerId;
    private String title;
    private String contents;
    private String modifiedAt;
    private String nickname;
    private String imgUrl;
    private String category;
    private Long likes ;
    private String img;

    public MyAnswerDto(Answer answer, User user, Long likes)
    {
        this.answerId = answer.getId();
        this.title = answer.getTitle();
        this.contents = answer.getContent();
        this.modifiedAt = TimeHandler.setModifiedAtLIst(answer.getModifiedAt());
        this.nickname = user.getNickName();
        this.imgUrl = HippoURL.name(user.getHippoName(), user.getHippoLevel() );
        this.category = answer.getPost().getCategory();
        this.likes = likes;
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