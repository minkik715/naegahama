package com.hanghae.naegahama.dto.answer;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.AnswerLike;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class AnswerDetailGetResponseDto
{
    private Long requestWriterId;

    private Long answerWriterId;

    private String title;

    private String content;

    private LocalDateTime modifiedAt;

    private String star;

    private Long answerLikeCount;

    private Long commentCount;

    private String answerWriter;

    private List<AnswerLike> likeUserId;

    public AnswerDetailGetResponseDto(Answer answer, Long likeCount, Long commentCount)
    {
        this.requestWriterId = answer.getPost().getUser().getId();
        this.answerWriterId = answer.getId();
        this.title = answer.getTitle();
        this.content = answer.getContent();
        this.modifiedAt = answer.getModifiedAt();
        this.star = answer.getStar();
        this.answerLikeCount = likeCount;
        this.commentCount= commentCount;
        this.answerWriter= answer.getUser().getNickName();
        this.likeUserId = answer.getLikeList();

    }
}