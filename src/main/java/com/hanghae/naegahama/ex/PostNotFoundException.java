package com.hanghae.naegahama.ex;

import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message) {
        super(message);
    }
}
