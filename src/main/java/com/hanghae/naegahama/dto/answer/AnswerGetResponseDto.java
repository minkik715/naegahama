package com.hanghae.naegahama.dto.answer;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

public class AnswerGetResponseDto
{
    private Long answerId;

    private String answerWriter;

    private String title;

    private LocalDateTime modifiedAt;

    private int answerLikeCount;

    private int commentCount;

    private int imageCount;

    public AnswerGetResponseDto(Answer answer, int commentCount, int likeCount,int imageCount)
    {
        this.answerId = answer.getId();
        this.answerWriter = answer.getUser().getNickName();
        this.title = answer.getTitle();
        this.modifiedAt = answer.getModifiedAt();
        this.commentCount = commentCount;
        this.answerLikeCount = likeCount;
        this.imageCount = imageCount;
    }
}
