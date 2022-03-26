package com.hanghae.naegahama.ex;

import lombok.Getter;

@Getter
public class DuplicationUserNameException extends RuntimeException{
    public DuplicationUserNameException(String message) {
        super(message);
    }
}
