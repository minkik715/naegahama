package com.hanghae.naegahama.ex;

import lombok.Getter;

@Getter
public class PasswordNotCollectException extends RuntimeException {

    public PasswordNotCollectException(String message) {
        super(message);}

}