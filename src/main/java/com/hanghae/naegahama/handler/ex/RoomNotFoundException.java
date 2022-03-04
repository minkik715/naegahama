package com.hanghae.naegahama.handler.ex;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(String message) {
        super(message);
    }
}
