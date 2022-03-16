package com.hanghae.naegahama.handler.ex;

import lombok.Getter;

@Getter
public class PostWriteLevelNullException extends RuntimeException{
    public PostWriteLevelNullException(String message) {
        super(message);
    }
}
