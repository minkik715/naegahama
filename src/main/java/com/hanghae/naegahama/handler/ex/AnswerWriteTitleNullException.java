package com.hanghae.naegahama.handler.ex;

import lombok.Getter;

@Getter
public class AnswerWriteTitleNullException extends RuntimeException{
    public AnswerWriteTitleNullException(String message) {
        super(message);
    }
}
