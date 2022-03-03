package com.hanghae.naegahama.dto.MyPage;

import com.hanghae.naegahama.domain.Answer;
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

    private Long hippolv;

    public MyBannerDto(User user)
    {
        this.nickname = user.getNickName();
        this.email = user.getEmail();
        this.hippoName = user.getHippoInfo().getHippoName();
        this.point = user.getPoint();
        this.hippolv = user.getHippoInfo().getHippolv();
    }
}
