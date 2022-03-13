package com.hanghae.naegahama.handler.ex;

import lombok.Getter;

@Getter
public class PostWriteTitleNullException extends RuntimeException{
    public PostWriteTitleNullException(String message) {
        super(message);
    }
}
