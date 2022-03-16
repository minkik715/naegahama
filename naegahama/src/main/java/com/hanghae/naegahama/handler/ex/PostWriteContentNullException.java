package com.hanghae.naegahama.handler.ex;

import lombok.Getter;

@Getter
public class PostWriteContentNullException extends RuntimeException{
    public PostWriteContentNullException(String message) {
        super(message);
    }
}
