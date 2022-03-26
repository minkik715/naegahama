package com.hanghae.naegahama.ex;

import lombok.Getter;

@Getter
public class PostWriteLevelNullException extends RuntimeException{
    public PostWriteLevelNullException(String message) {
        super(message);
    }
}
