package com.hanghae.naegahama.handler.ex;

import lombok.Getter;

@Getter
public class PasswordNotCollectException extends RuntimeException {

    private String message = "비밀번호가 일치하지 않습니다.";

}