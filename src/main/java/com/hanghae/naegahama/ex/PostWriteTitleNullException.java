package com.hanghae.naegahama.ex;

import lombok.Getter;

@Getter
public class PostWriteTitleNullException extends RuntimeException{
    public PostWriteTitleNullException(String message) {
        super(message);
    }
}
