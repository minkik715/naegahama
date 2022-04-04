package com.hanghae.naegahama.ex;

import lombok.Getter;

@Getter
public class PostWriteCategoryNullException extends RuntimeException{
    public PostWriteCategoryNullException(String message) {
        super(message);
    }
}
