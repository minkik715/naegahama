package com.hanghae.naegahama.handler.ex;

import lombok.Getter;

@Getter
public class AnswerStarExistException extends RuntimeException{
    public AnswerStarExistException(String message) {
        super(message);
    }
}
