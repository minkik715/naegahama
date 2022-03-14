package com.hanghae.naegahama.handler.ex;

import lombok.Getter;

@Getter
public class AnswerWritePostClosedException extends RuntimeException{
    public AnswerWritePostClosedException(String message) {
        super(message);
    }
}
