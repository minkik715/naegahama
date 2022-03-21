package com.hanghae.naegahama.dto.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerVideoEncoding
{
    Long answerID;

    public AnswerVideoEncoding(Long answerID)
    {

        this.answerID = answerID;
    }
}
