package com.hanghae.naegahama.ex;

import lombok.Getter;

@Getter
public class PasswordCheckFailException extends RuntimeException{
    public PasswordCheckFailException(String message) {
        super(message);
    }
}
