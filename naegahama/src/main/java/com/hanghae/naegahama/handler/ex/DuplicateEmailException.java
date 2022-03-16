package com.hanghae.naegahama.handler.ex;

import lombok.Getter;

@Getter
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}