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

    public MyBannerDto(User user)
    {
        this.nickname = user.getNickName();
        this.email = user.getEmail();
        this.hippoName = user.getHippoName();
        this.point = user.getPoint();
        this.hippolv = user.getHippoLevel();
    }
}
