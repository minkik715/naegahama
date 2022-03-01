package com.hanghae.naegahama.dto.answer;

import com.hanghae.naegahama.domain.Answer;
import com.hanghae.naegahama.domain.User;

import java.time.LocalDateTime;

public class AnswerGetResponseDto
{
    private Long answerId;

    private String answerWriter;

    private String title;

    private LocalDateTime modifiedAt;

    private Long likeCount;

    private Long commentCount;

    public AnswerGetResponseDto(Answer answer, Long commentCount, Long likeCount)
    {
        this.answerId = answer.getId();
        this.answerWriter = answer.getUser().getNickName();
        this.title = answer.getTitle();
        this.modifiedAt = answer.getModifiedAt();
        this.commentCount = commentCount;
        this.likeCount = likeCount;

    }
}
