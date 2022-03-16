package com.hanghae.naegahama.handler.ex;

import lombok.Getter;

@Getter
public class SearchNotFoundException extends RuntimeException {
    public SearchNotFoundException(String message) {
            super(message);
        }
}
