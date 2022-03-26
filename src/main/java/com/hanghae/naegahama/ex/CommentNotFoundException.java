package com.hanghae.naegahama.ex;

import lombok.Getter;

@Getter
public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}