package com.hanghae.naegahama.dto.MyPage;

import com.hanghae.naegahama.domain.Post;
import com.hanghae.naegahama.domain.User;
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
    private String nickname;
    private String imgUrl;
    private String category;

//    private Long likeCount;
//    private Integer answerCount ;

    public MyPostDto(Post post, User user )
    {
        this.requestId = post.getId();
        this.title = post.getTitle();
        this.modifiedAt = post.getModifiedAt();
        this.nickname = user.getNickName();
        this.imgUrl = user.getHippoName();
        this.category = post.getCategory();
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