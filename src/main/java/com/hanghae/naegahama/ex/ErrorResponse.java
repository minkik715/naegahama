package com.hanghae.naegahama.ex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ErrorResponse {

    private String Status;
    private String message;

}