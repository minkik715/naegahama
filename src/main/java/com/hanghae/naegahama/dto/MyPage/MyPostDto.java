package com.hanghae.naegahama.dto.MyPage;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
import com.hanghae.naegahama.initial.HippoURL;
import com.hanghae.naegahama.util.TimeHandler;
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
    private String modifiedAt;
    private String nickname;
    private String imgUrl;
    private String category;

//    private Long likeCount;
//    private Integer answerCount ;

    public MyPostDto(Post post, User user )
    {
        this.requestId = post.getId();
        this.title = post.getTitle();
        this.modifiedAt = TimeHandler.setModifiedAtLIst(post.getModifiedAt());
        this.nickname = user.getNickName();
        this.category = post.getCategory();
        this.imgUrl = HippoURL.name(user.getHippoName(), user.getHippoLevel() );
//        this.likeCount = likeCount;

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