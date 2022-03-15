package com.hanghae.naegahama.dto.rank;

import com.hanghae.naegahama.domain.RankStatus;
import com.hanghae.naegahama.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class RankResponseDto {
    private int rank;
    private String nickname;
    private String hippoName;
    private int point;
    private RankStatus Status;
    private Boolean is_changed;
    public RankResponseDto(User user, int i, RankStatus status, Boolean is_changed ) {
        this.rank = i;
        this.nickname = user.getNickName();
        this.point = user.getPoint();
        this.Status = status;
        this.is_changed = is_changed;
        this.hippoName = user.getHippoName();
    }
}
