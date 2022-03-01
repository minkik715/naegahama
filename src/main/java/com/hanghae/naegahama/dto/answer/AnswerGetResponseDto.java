package com.hanghae.naegahama.dto.answer;

import com.hanghae.naegahama.domain.Answer;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
public class AnswerGetResponseDto
{
    private Long answerId;

    private String answerWriter;

    private String title;

    private LocalDateTime modifiedAt;

    private Long answerLikeCount;

    private Long commentCount;

    public AnswerGetResponseDto(Answer answer, Long commentCount, Long likeCount)
    {
        this.answerId = answer.getId();
        this.answerWriter = answer.getUser().getNickName();
        this.title = answer.getTitle();
        this.modifiedAt = answer.getModifiedAt();
        this.commentCount = commentCount;
        this.answerLikeCount = likeCount;
    }
}
