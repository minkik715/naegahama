package com.hanghae.naegahama.dto.rank;

import com.hanghae.naegahama.domain.RankStatus;
import com.hanghae.naegahama.domain.User;
import lombok.Getter;

@Getter
public class RankResponseDto {
    private int rank;
    private String nickname;
    private int point;
    private RankStatus Status;

    public RankResponseDto(User user, int i, RankStatus status) {
        this.rank = i;
        this.nickname = user.getNickName();
        this.point = user.getPoint();
        this.Status = status;
    }
}
