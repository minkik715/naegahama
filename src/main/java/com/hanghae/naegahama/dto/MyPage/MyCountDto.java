package com.hanghae.naegahama.dto.MyPage;

import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyCountDto
{
    private String nickname;
    private String hippoName;
    private Long postCount;
    private Long answerCount;
    private String hippoImage;
    
    private Long userId;

    public MyCountDto(User user, Long postCount, Long answerCount )
    {
        this.nickname = user.getNickName();
        this.hippoName = user.getHippoName();
        this.postCount = postCount;
        this.answerCount = answerCount;
        this.hippoImage = user.getHippoImage();
        this.userId = user.getId();

    }
}
