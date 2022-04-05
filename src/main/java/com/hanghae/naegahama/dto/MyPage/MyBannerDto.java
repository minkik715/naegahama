package com.hanghae.naegahama.dto.MyPage;

import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
public class MyBannerDto
{
    private String nickname;

    private String email;

    private String hippoName;

    private int point;

    private int hippolv;

    private String category;

    private ArrayList<String> expert;

    private String imgUrl;
    private Long userId;

    public MyBannerDto(User user,ArrayList<String> expert,int point)
    {
        this.nickname = user.getNickName();
        this.email = user.getEmail();
        this.hippoName = user.getHippoName();
        this.point = point;
        this.hippolv = user.getHippoLevel();
        this.category = user.getCategory();
        this.imgUrl = user.getHippoImage();
        this.expert = expert;
        this.userId = user.getId();
    }
}
