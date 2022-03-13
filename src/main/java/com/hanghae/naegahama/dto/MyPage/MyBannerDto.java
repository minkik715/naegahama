package com.hanghae.naegahama.dto.MyPage;

import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyBannerDto
{
    private String nickname;

    private String email;

    private String hippoName;

    private int point;

    private int hippolv;

    private String category;

    private int[] expert = new int[12];

    private String imgUrl;

    public MyBannerDto(User user,int[] expert,int point)
    {
        this.nickname = user.getNickName();
        this.email = user.getEmail();
        this.hippoName = user.getHippoName();
        this.point =
        this.hippolv = user.getHippoLevel();
        this.category = user.getCategory();
        this.imgUrl = null;
        this.expert = expert;
    }
}
