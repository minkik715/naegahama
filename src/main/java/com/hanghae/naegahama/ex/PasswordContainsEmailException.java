package com.hanghae.naegahama.ex;

import lombok.Getter;

@Getter
public class PasswordContainsEmailException extends RuntimeException {
    public PasswordContainsEmailException(String message) {
        super(message);
    }
}
